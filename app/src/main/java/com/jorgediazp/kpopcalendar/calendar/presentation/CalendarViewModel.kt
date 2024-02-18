package com.jorgediazp.kpopcalendar.calendar.presentation

import androidx.compose.ui.text.toLowerCase
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.crashlytics.ktx.crashlytics
import com.google.firebase.ktx.Firebase
import com.google.firebase.remoteconfig.FirebaseRemoteConfig
import com.jorgediazp.kpopcalendar.R
import com.jorgediazp.kpopcalendar.calendar.presentation.model.CalendarScreenBackgroundState
import com.jorgediazp.kpopcalendar.calendar.presentation.model.CalendarScreenForegroundState
import com.jorgediazp.kpopcalendar.calendar.presentation.model.DatePresentationModel
import com.jorgediazp.kpopcalendar.common.domain.model.SongDomainModel
import com.jorgediazp.kpopcalendar.common.domain.usecase.DeleteLikedSongsUseCase
import com.jorgediazp.kpopcalendar.common.domain.usecase.FirebaseSignInUseCase
import com.jorgediazp.kpopcalendar.common.domain.usecase.GetLikedSongsUseCase
import com.jorgediazp.kpopcalendar.common.domain.usecase.GetSongsUseCase
import com.jorgediazp.kpopcalendar.common.domain.usecase.InsertLikedSongsUseCase
import com.jorgediazp.kpopcalendar.common.domain.usecase.NotificationsPermissionUseCase
import com.jorgediazp.kpopcalendar.common.presentation.model.PresentationModelExtensions.Companion.toPresentationModel
import com.jorgediazp.kpopcalendar.common.presentation.model.SongPresentationModel
import com.jorgediazp.kpopcalendar.common.util.DataResult
import com.jorgediazp.kpopcalendar.common.util.DateUtils.Companion.MONTH_AND_YEAR_DATE_FORMAT
import com.jorgediazp.kpopcalendar.common.util.DateUtils.Companion.MONTH_DATE_FORMAT
import com.jorgediazp.kpopcalendar.common.util.DateUtils.Companion.SONG_DATE_FORMAT
import com.jorgediazp.kpopcalendar.common.util.Event
import com.jorgediazp.kpopcalendar.common.util.FirebaseRemoteConfigKey
import com.jorgediazp.kpopcalendar.common.util.FirebaseUtils.Companion.getRemoteConfigLong
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch
import java.time.Instant
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.ZoneId
import java.time.ZoneOffset
import java.time.format.DateTimeFormatter
import java.time.format.TextStyle
import java.util.Locale
import javax.inject.Inject

@HiltViewModel
class CalendarViewModel @Inject constructor(
    private val getSongsUseCase: GetSongsUseCase,
    private val getLikedSongsUseCase: GetLikedSongsUseCase,
    private val insertLikedSongsUseCase: InsertLikedSongsUseCase,
    private val deleteLikedSongsUseCase: DeleteLikedSongsUseCase,
    private val firebaseSignInUseCase: FirebaseSignInUseCase,
    private val notificationsPermissionUseCase: NotificationsPermissionUseCase
) : ViewModel() {

    val backgroundState =
        MutableStateFlow<CalendarScreenBackgroundState>(CalendarScreenBackgroundState.ShowNothing)
    val foregroundState =
        MutableStateFlow<CalendarScreenForegroundState>(CalendarScreenForegroundState.ShowNothing)
    val showSnackBarEvent = MutableStateFlow(Event<Int?>(null))

    var dataLoaded = false
    private var likedSongIds: List<Int> = listOf()

    fun loadData() {
        FirebaseRemoteConfig.getInstance().fetchAndActivate()
        viewModelScope.launch {
            foregroundState.value = CalendarScreenForegroundState.ShowLoading

            if (notificationsPermissionUseCase.requestNotificationsPermission()) {
                foregroundState.value = CalendarScreenForegroundState.ShowNotificationsPermissionDialog

            } else if (firebaseSignInUseCase.signIn()) {
                loadLikedSongs()

            } else {
                backgroundState.value = CalendarScreenBackgroundState.ShowDefaultError
                foregroundState.value = CalendarScreenForegroundState.ShowNothing
            }
        }
    }

    fun loadDateList(selectedDateMillis: Long?) {
        selectedDateMillis?.let {
            loadDateList(selectedDateMillis)
        } ?: {
            foregroundState.value = CalendarScreenForegroundState.ShowNothing
        }
    }

    fun loadDatePicker(selectedDateMillis: Long) {
        val minTimestamp = getRemoteConfigLong(FirebaseRemoteConfigKey.CALENDAR_MIN_TIME)
        val maxTimestamp = getRemoteConfigLong(FirebaseRemoteConfigKey.CALENDAR_MAX_TIME)
        foregroundState.value =
            CalendarScreenForegroundState.ShowCalendarPicker(
                initialSelectedDateMillis = selectedDateMillis,
                yearRange = getCalendarPickerYearRange(minTimestamp, maxTimestamp),
                minTimestamp = minTimestamp,
                maxTimestamp = maxTimestamp
            )
    }

    fun goToToday() {
        backgroundState.value = CalendarScreenBackgroundState.ShowNothing
        loadDateList(Instant.now().toEpochMilli())
    }

    fun insertOrDeleteLikedSong(songPresentation: SongPresentationModel) {
        viewModelScope.launch {
            if (backgroundState.value is CalendarScreenBackgroundState.ShowDateList) {
                (backgroundState.value as CalendarScreenBackgroundState.ShowDateList).let { state ->

                    state.songDomainIndexedMap[songPresentation.id]?.let { songDomain ->
                        val result: DataResult<Nothing>
                        val snackBarResId: Int
                        if (!songPresentation.liked) {
                            result = insertLikedSongsUseCase.insertLikedSong(songDomain)
                            snackBarResId = R.string.liked_snackbar_song_added
                        } else {
                            result = deleteLikedSongsUseCase.deleteLikedSong(songDomain.id)
                            snackBarResId = R.string.liked_snackbar_song_removed
                        }

                        if (result is DataResult.Success) {
                            showSnackBarEvent.value = Event(snackBarResId)
                        }
                    }
                }
            }
        }
    }

    fun cancelNotificationsPermission() {
        viewModelScope.launch {
            notificationsPermissionUseCase.updateNotificationsPermissionsFlag(
                NotificationsPermissionUseCase.Status.CANCELED)
            loadData()
        }
    }

    private suspend fun loadLikedSongs() {
        getLikedSongsUseCase.getAllLikedSongIdsFlow()
            .catch { throwable ->
                Firebase.crashlytics.recordException(throwable)
                likedSongIds = listOf()

            }.collect {
                likedSongIds = it
                if (!dataLoaded) {
                    dataLoaded = true
                    goToToday()
                } else if (backgroundState.value is CalendarScreenBackgroundState.ShowDateList) {
                    updateLikedSongs(
                        backgroundState.value as CalendarScreenBackgroundState.ShowDateList,
                        likedSongIds
                    )
                }
            }
    }

    private fun getCalendarPickerYearRange(minTimestamp: Long, maxTimestamp: Long): IntRange {
        val minDateTime = LocalDateTime.ofInstant(
            Instant.ofEpochMilli(minTimestamp),
            ZoneId.of(ZoneOffset.UTC.id)
        )
        val maxDateTime = LocalDateTime.ofInstant(
            Instant.ofEpochMilli(maxTimestamp),
            ZoneId.of(ZoneOffset.UTC.id)
        )
        return IntRange(start = minDateTime.year, endInclusive = maxDateTime.year)
    }

    private fun loadDateList(selectedDateMillis: Long) {
        val selectedDateTime = LocalDateTime.ofInstant(
            Instant.ofEpochMilli(selectedDateMillis),
            ZoneId.systemDefault()
        )
        foregroundState.value = CalendarScreenForegroundState.ShowLoading

        viewModelScope.launch {
            val result =
                getSongsUseCase.getComebackMapByMonth(
                    selectedDateTime.year,
                    selectedDateTime.monthValue
                )
            if (result is DataResult.Success && result.data != null) {
                val dateList = getDatePresentationList(selectedDateTime, result.data, likedSongIds)
                val selectedDateIndex = getSelectedDateIndex(dateList)
                val songDomainIndexedMap = getSongDomainIndexedMap(result.data)

                backgroundState.value = CalendarScreenBackgroundState.ShowDateList(
                    selectedDateIndex = selectedDateIndex,
                    topBarTitle = getTopBarTitle(selectedDateTime),
                    todayDayString = getTodayDayString(),
                    selectedDateMillis = selectedDateMillis,
                    dateList = dateList,
                    songDomainIndexedMap = songDomainIndexedMap
                )

            } else {
                backgroundState.value =
                    CalendarScreenBackgroundState.ShowError(
                        topBarTitle = getTopBarTitle(selectedDateTime),
                        todayDayString = getTodayDayString(),
                        selectedDateMillis = selectedDateMillis
                    )
            }
            foregroundState.value = CalendarScreenForegroundState.ShowNothing
        }
    }

    private fun getTopBarTitle(dateTime: LocalDateTime): String {
        val pattern =
            if (dateTime.year == LocalDateTime.now().year) MONTH_DATE_FORMAT else MONTH_AND_YEAR_DATE_FORMAT
        return dateTime.format(DateTimeFormatter.ofPattern(pattern))
            .replaceFirstChar(Char::uppercase)
    }

    private fun getDatePresentationList(
        selectedDateTime: LocalDateTime,
        domainMap: Map<String, List<SongDomainModel>>,
        likedSongIds: List<Int>
    ): List<DatePresentationModel> {
        val dateList = mutableListOf<DatePresentationModel>()
        var isOddRow = true
        domainMap.forEach { (dateString, songDomainList) ->
            val songPresentationList = mutableListOf<SongPresentationModel>()
            // Sort alphabetically
            val auxSongDomainList = songDomainList.sortedBy { it.artist?.lowercase() ?: it.artists?.joinToString()?.lowercase()  }
            auxSongDomainList.forEach { songDomain ->
                try {
                    songPresentationList.add(
                        songDomain.toPresentationModel(
                            isOddRow,
                            likedSongIds.contains(songDomain.id)
                        )
                    )
                    isOddRow = !isOddRow
                } catch (e: Exception) {
                    Firebase.crashlytics.recordException(e)
                }
            }
            dateList.add(
                DatePresentationModel(
                    date = getDisplayDate(dateString),
                    isSelectedDate = dateStringIsSelectedDate(selectedDateTime, dateString),
                    isToday = dateStringIsToday(dateString),
                    songPresentationList
                )
            )
        }
        return dateList
    }

    private fun getDisplayDate(dateString: String): String {
        val dateFormatter = DateTimeFormatter.ofPattern(SONG_DATE_FORMAT)
        val date = LocalDate.parse(dateString, dateFormatter)
        return "${
            date.dayOfWeek.getDisplayName(
                TextStyle.FULL,
                Locale.getDefault()
            ).replaceFirstChar(Char::uppercase)
        } ${date.dayOfMonth}"
    }

    private fun dateStringIsToday(dateString: String): Boolean {
        val dateFormatter = DateTimeFormatter.ofPattern(SONG_DATE_FORMAT)
        val date = LocalDate.parse(dateString, dateFormatter)
        return date.equals(LocalDate.now(ZoneId.systemDefault()))
    }

    private fun dateStringIsSelectedDate(
        selectedDateTime: LocalDateTime,
        dateString: String
    ): Boolean {
        val dateFormatter = DateTimeFormatter.ofPattern(SONG_DATE_FORMAT)
        return selectedDateTime.format(dateFormatter) == dateString
    }

    private fun getSelectedDateIndex(dateList: List<DatePresentationModel>): Int {
        var index = 0
        run breaking@{
            dateList.forEach { dateModel ->
                if (dateModel.isSelectedDate) {
                    return@breaking
                }
                // one for each stickyHeader
                index++
                if (dateModel.songList.isEmpty()) {
                    // one for the displayed SongsEmptyCard
                    index++
                } else {
                    // each song or teaser card displayed
                    index += dateModel.songList.size
                }
            }
        }
        return index
    }

    private fun getTodayDayString(): String {
        return LocalDateTime.now(ZoneId.systemDefault()).dayOfMonth.toString()
    }

    private fun getSongDomainIndexedMap(domainMap: Map<String, List<SongDomainModel>>): Map<Int, SongDomainModel> {
        val indexedMap = mutableMapOf<Int, SongDomainModel>()
        domainMap.values.forEach { list ->
            list.forEach { song ->
                indexedMap[song.id] = song
            }
        }
        return indexedMap
    }

    private fun updateLikedSongs(
        dateListState: CalendarScreenBackgroundState.ShowDateList,
        likedSongIds: List<Int>
    ) {
        dateListState.dateList.forEach { dateModel ->
            dateModel.songList.forEach { songPresentation ->
                songPresentation.liked = likedSongIds.contains(songPresentation.id)
            }
        }
        backgroundState.value = dateListState.copy(update = dateListState.update + 1)
    }
}
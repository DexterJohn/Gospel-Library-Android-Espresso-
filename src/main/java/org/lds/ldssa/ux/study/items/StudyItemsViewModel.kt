package org.lds.ldssa.ux.study.items

import android.app.Application
import android.arch.lifecycle.AndroidViewModel
import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import kotlinx.coroutines.experimental.launch
import org.lds.ldssa.ux.study.plans.StudyPlanDto
import org.lds.ldssa.ux.study.plans.StudyPlansViewModel
import org.lds.mobile.coroutine.CoroutineContextProvider
import org.threeten.bp.LocalDate
import javax.inject.Inject

class StudyItemsViewModel @Inject constructor(app: Application, var cc: CoroutineContextProvider) : AndroidViewModel(app) {
    val livePlanTitle: LiveData<String> = MutableLiveData()
    val liveStudyLessonList: LiveData<List<StudyLessonDto>> = MutableLiveData()

    var scrollPosition = 0

    lateinit var studyPlan: StudyPlanDto

    fun init(studyPlanId: Long): Unit {
        loadStudyPrinciples(studyPlanId)
    }

    private fun loadStudyPrinciples(studyPlanId: Long) = launch(cc.commonPool) {
        when (studyPlanId) {
            StudyPlansViewModel.bofmSubscription.studyPlanId -> loadBofm()
            StudyPlansViewModel.cfmOtSubscription.studyPlanId -> loadCfmOt()
        }
    }

    //todo temporary until we have real StudyPlan data
    private fun loadBofm() {
        studyPlan = StudyPlansViewModel.bofmPlan
        val livePlanTitle = livePlanTitle as MutableLiveData
        livePlanTitle.postValue(StudyPlansViewModel.bofmSubscription.title)
        val lastCompletedItem = StudyPlansViewModel.bofmSubscription.lastCompletedItem

        // From http://mormono.com/bookofmormon365/
        val bomPrincipleNames = arrayOf(
                "1 Nephi 1;1-ne/1", "1 Nephi 2;1-ne/2", "1 Nephi 3:1–14;1-ne/3?verse1-14", "1 Nephi 3:15–31;1-ne/3?verse15-13#15", "1 Nephi 4:1–18;1-ne/4?verse1-18", "1 Nephi 4:19–38;1-ne/4?verse19-38#19", "1 Nephi 5;1-ne/5",
                "1 Nephi 6, 1 Nephi 7", "1 Nephi 8:1–19", "1 Nephi 8:20–38", "1 Nephi 9, 1 Nephi 10", "1 Nephi 11:1–20", "1 Nephi 11:21–36", "1 Nephi 12",
                "1 Nephi 13:1–11", "1 Nephi 13:12–29", "1 Nephi 13:30–42", "1 Nephi 14:1–15", "1 Nephi 14:16–30", "1 Nephi 15:1–20", "1 Nephi 15:21–36",
                "1 Nephi 16:1–20", "1 Nephi 16:21–39", "1 Nephi 17:1–18", "1 Nephi 17:19–35", "1 Nephi 17:36–55", "1 Nephi 18:1–11", "1 Nephi 18: 12–25",
                "1 Nephi 19:1–10", "1 Nephi 19:11–24", "1 Nephi 20", "1 Nephi 21:1–13", "1 Nephi 21:14–26", "1 Nephi 22:1–14", "1 Nephi 22:15–31", "2 Nephi 1:1–16",
                "2 Nephi 1:17–32", "2 Nephi 2:1–16", "2 Nephi 2:17–30", "2 Nephi 3", "2 Nephi 4:1–18", "2 Nephi 4:19–35", "2 Nephi 5:1–17",
                "2 Nephi 5:18–34", "2 Nephi 6, 2 Nephi 7", "2 Nephi 8", "2 Nephi 9:1–18", "2 Nephi 9:19–36", "2 Nephi 9:37–54", "2 Nephi 10",
                "2 Nephi 11", "2 Nephi 12", "2 Nephi 13", "2 Nephi 14, 2 Nephi 15", "2 Nephi 16, 2 Nephi 17", "2 Nephi 18", "2 Nephi 19",
                "2 Nephi 20", "2 Nephi 21, 2 Nephi 22", "2 Nephi 23", "2 Nephi 24:1–17", "2 Nephi 24:18–32", "2 Nephi 25:1–15", "2 Nephi 25:16–30",
                "2 Nephi 26", "2 Nephi 27:1–18", "2 Nephi 27:19–35", "2 Nephi 28:1–16", "2 Nephi 28:17–32", "2 Nephi 29", "2 Nephi 30",
                "2 Nephi 31", "2 Nephi 32", "2 Nephi 33", "Jacob 1", "Jacob 2:1–17", "Jacob 2:18–35", "Jacob 3",
                "Jacob 4", "Jacob 5:1–16", "Jacob 5:17–32", "Jacob 5:33–48", "Jacob 5:49–64", "Jacob 5:65–77", "Jacob 6",
                "Jacob 7:1–13", "Jacob 7:14–27", "Enos 1", "Jarom 1", "Omni 1:1–14", "Omni 1:15–30", "Words of Mormon 1",
                "Mosiah 1", "Mosiah 2:1–13", "Mosiah 2:14–27", "Mosiah 2:28–41", "Mosiah 3:1–13", "Mosiah 3:14–27", "Mosiah 4:1–15",
                "Mosiah 4:16–30", "Mosiah 5", "Mosiah 6", "Mosiah 7:1–16", "Mosiah 7:17–33", "Mosiah 8", "Mosiah 9",
                "Mosiah 10", "Mosiah 11:1–15", "Mosiah 11:16–29", "Mosiah 12:1–18", "Mosiah 12:19–37", "Mosiah 13:1–24", "Mosiah 13:25–35, Mosiah 14",
                "Mosiah 15", "Mosiah 16", "Mosiah 17", "Mosiah 18:1–17", "Mosiah 18:18–35", "Mosiah 19", "Mosiah 20",
                "Mosiah 21:1–18", "Mosiah 21:19–36", "Mosiah 22", "Mosiah 23:1–19", "Mosiah 23:20–39", "Mosiah 24", "Mosiah 25",
                "Mosiah 26:1–20", "Mosiah 26:21–39", "Mosiah 27:1–18", "Mosiah 27:19–37", "Mosiah 28", "Mosiah 29:1–15", "Mosiah 29:16–30",
                "Mosiah 29:31–47", "Alma 1:1–18", "Alma 1:19–33", "Alma 2:1–19", "Alma 2:20–38", "Alma 3:1–12", "Alma 3:13–27",
                "Alma 4", "Alma 5:1–21", "Alma 5:22–42", "Alma 5:43–62", "Alma 6", "Alma 7:1–14", "Alma 7:15–27",
                "Alma 8:1–17", "Alma 8:18–32", "Alma 9:1–17", "Alma 9:18–34", "Alma 10:1–14", "Alma 10:15–32", "Alma 11:1–22",
                "Alma 11:23–46", "Alma 12:1–19", "Alma 12:20–37", "Alma 13:1–16", "Alma 13:17–31", "Alma 14:1–16", "Alma 14:17–29",
                "Alma 15", "Alma 16:1–11", "Alma 16:12–21", "Alma 17:1–19", "Alma 17:20–39", "Alma 18:1–15", "Alma 18:16–28",
                "Alma 18:29–43", "Alma 19:1–18", "Alma 19:19–36", "Alma 20:1–15", "Alma 20:16–30", "Alma 21", "Alma 22:1–18",
                "Alma 22:19–35", "Alma 23", "Alma 24:1–16", "Alma 24:17–30", "Alma 25", "Alma 26:1–20", "Alma 26:21–37",
                "Alma 27:1–15", "Alma 27:16–30", "Alma 28", "Alma 29", "Alma 30:1–16", "Alma 30:17–30", "Alma 30:31–45",
                "Alma 30:46–60", "Alma 31:1–18", "Alma 31:19–38", "Alma 32:1–15", "Alma 32:16–29", "Alma 32:30–43", "Alma 33",
                "Alma 34:1–21", "Alma 34:22–41", "Alma 35", "Alma 36:1–15", "Alma 36:16–30", "Alma 37:1–15", "Alma 37:16–31",
                "Alma 37:32–47", "Alma 38", "Alma 39", "Alma 40", "Alma 41", "Alma 42:1–16", "Alma 42:17–31",
                "Alma 43:1–18", "Alma 43:19–35", "Alma 43:36–54", "Alma 44:1–11", "Alma 44:12–24", "Alma 45", "Alma 46:1–21",
                "Alma 46:22–41", "Alma 47:1–18", "Alma 47:19–36", "Alma 48:1–13", "Alma 48:14–25", "Alma 49:1–15", "Alma 49:16–30",
                "Alma 50:1–21", "Alma 50:22–40", "Alma 51:1–21", "Alma 51:22–37", "Alma 52:1–20", "Alma 52:21–40", "Alma 53",
                "Alma 54", "Alma 55:1–17", "Alma 55:18–35", "Alma 56:1–20", "Alma 56:21–40", "Alma 56:41–57", "Alma 57:1–18",
                "Alma 57:19–36", "Alma 58:1–23", "Alma 58:24–41", "Alma 59", "Alma 60:1–19", "Alma 60:20–36", "Alma 61",
                "Alma 62:1–18", "Alma 62:19–36", "Alma 62:37–52", "Alma 62", "Helaman 1:1–17", "Helaman 1:18–34", "Helaman 2",
                "Helaman 3:1–18", "Helaman 3:19–37", "Helaman 4:1–13", "Helaman 4:14–26", "Helaman 5:1–16", "Helaman 5:17–34", "Helaman 5:35–52",
                "Helaman 6:1–20", "Helaman 6:21–41", "Helaman 7:1–15", "Helaman 7:16–29", "Helaman 8", "Helaman 9:1–20", "Helaman 9:21–41",
                "Helaman 10;hel/10", "Helaman 11:1–19;hel/11", "Helaman 11:20–38;hel/11#20", "Helaman 12:1–14;hel/12", "Helaman 12:15–26;hel/12#15", "Helaman 13:1–20;hel/13", "Helaman 13:21–39;hel/13#21",
                "Helaman 14:1–15;hel/14", "Helaman 14:16–31;hel/14#16", "Helaman 15;hel/15", "Helaman 16;hel/16", "3 Nephi 1:1–15;3-ne/1", "3 Nephi 1:16–30;3-ne/1#16", "3 Nephi 2;3-ne/2",
                "3 Nephi 3;3-ne/3", "3 Nephi 4:1–16;3-ne/4", "3 Nephi 4:17–33;3-ne/4#17", "3 Nephi 5:1–13;3-ne/5", "3 Nephi 5:14–26;3-ne/5#14", "3 Nephi 6:1–16;3-ne/6", "3 Nephi 6:17–30;3-ne/6#17",
                "3 Nephi 7:1–13", "3 Nephi 7:14–26", "3 Nephi 8", "3 Nephi 9", "3 Nephi 10", "3 Nephi 11:1–20", "3 Nephi 11:21–41",
                "3 Nephi 12:1–24", "3 Nephi 12:25–48", "3 Nephi 13:1–15", "3 Nephi 13:16–34", "3 Nephi 14", "3 Nephi 15", "3 Nephi 16",
                "3 Nephi 17", "3 Nephi 18:1–20", "3 Nephi 18:21–39", "3 Nephi 19:1–18", "3 Nephi 19:19–36", "3 Nephi 20:1–23", "3 Nephi 20:24–46",
                "3 Nephi 21", "3 Nephi 22", "3 Nephi 23", "3 Nephi 24, 3 Nephi 25", "3 Nephi 26", "3 Nephi 27:1–17", "3 Nephi 27:18–33",
                "3 Nephi 28:1–23", "3 Nephi 28:24–40", "3 Nephi 29, 3 Nephi 30", "4 Nephi 1:1–26", "4 Nephi 1:27–49", "Mormon 1", "Mormon 2:1–15",
                "Mormon 2:16–29", "Mormon 3", "Mormon 4", "Mormon 5:1–11", "Mormon 5:12–24", "Mormon 6", "Mormon 7",
                "Mormon 8:1–21", "Mormon 8:22–41", "Mormon 9:1–20", "Mormon 9:21–37", "Ether 1:1–32", "Ether 1:33–43", "Ether 2",
                "Ether 3:1–13", "Ether 3:14–28", "Ether 4", "Ether 5", "Ether 6:1–15", "Ether 6:16–30", "Ether 7",
                "Ether 8", "Ether 9:1–19", "Ether 9:20–35", "Ether 10:1–18", "Ether 10:19–34", "Ether 11", "Ether 12:1–22",
                "Ether 12:23–41", "Ether 13:1–15", "Ether 13:16–31", "Ether 14", "Ether 15:1–17", "Ether 15:18–34", "Moroni 1, Moroni 2, Moroni 3",
                "Moroni 4, Moroni 5, Moroni 6", "Moroni 7:1–24", "Moroni 7:25–48", "Moroni 8", "Moroni 9", "Moroni 10:1–17", "Moroni 10:18–34"
        )
        val uriPrefix = "gospellibrary://content/scriptures/bofm/"

        val studyPlanLessons: MutableList<StudyLessonDto> = ArrayList()
        var week = 1
        var position = 1
        val date = LocalDate.now()
        bomPrincipleNames.map {
            val completedDate = if (position <= lastCompletedItem) date else null
            val index = it.indexOf(';')
            if (index > 0) {
                StudyPrincipleDto(title = it.substring(0, index), completionDate = completedDate, position = position++, contentUri = uriPrefix + it.substring(index + 1))
            } else {
                StudyPrincipleDto(title = it, completionDate = completedDate, position = position++)
            }
        }.groupBy {
            (it.position - 1) / 7
        }.entries.forEach { entry ->
            studyPlanLessons.add(StudyLessonDto(studyPlanId = studyPlan!!.id, title = "Week ${week++}", principleList = entry.value))
        }

        val liveStudyPlanList = this@StudyItemsViewModel.liveStudyLessonList as MutableLiveData
        liveStudyPlanList.postValue(studyPlanLessons)
    }

    //todo temporary until we have real StudyPlan data
    private fun loadCfmOt() {
        studyPlan = StudyPlansViewModel.cfmOtPlan
        val livePlanTitle = livePlanTitle as MutableLiveData
        livePlanTitle.postValue(StudyPlansViewModel.cfmOtSubscription.title)
        val lastCompletedItem = StudyPlansViewModel.cfmOtSubscription.lastCompletedItem

        val cfmLessonData = arrayOf(
                "February 27-March 5/We Are Responsible for Our Own Learning",
                "March 6-12/Introduction to the Old Testament",
                "March 13-19/Moses 1; Abraham 3: \"This is My Work and My Glory\"",
                "March 20-26/Genesis 1-2; Moses 2-3; Abraham 4-5: The Creation",
                "March 27-April 2/Genesis 3-4; Moses 4-5: The Fall of Adam and Eve",
                "April 10-16/Easter: \"He Will Swallow Up Death in Victory\"",
                "April 17-23/Genesis 5; Moses 6: \"Teach These Things Freely unto Your Children\"",
                "April 24-30/Moses 7: \"The Lord Called His People Zion\"",
                "May 1-7/Genesis 6-11; Moses 8: Living Righteously in a Wicked World",
                "May 8-14/Genesis 12-17; Abraham 1-2: Seek for the Blessings of the Fathers",
                "May 15-21/Genesis 18-23: \"Now I Know That Thou Fearest God\"",
                "May 22-28/Genesis 24-33: The Covenant Is Renewed",
                "May 29-June 4/Genesis 37-41: \"And The Lord Was with Joseph\"",
                "June 5-11/Genesis 42-50: \"Forgive ... the Trespass of the Brethren\"",
                "June 12-18/Exodus 1-6: \"Certainly I Will Be with Thee\"",
                "June 19-25/Exodus 7-15: \"Let My People Go\""
        )
        val firstSubItemId = 7L

        var position = 1
        val date = LocalDate.now()
        val studyPlanLessons = cfmLessonData.map {
            val completedDate = if (position <= lastCompletedItem) date else null
            val index = it.indexOf('/')
            val principle = if (index > 0) {
                StudyPrincipleDto(title = it.substring(index + 1), completionDate = completedDate, position = position, subItemId = firstSubItemId + position++)
            } else {
                error("Missing slash / in '$it'")
            }
            StudyLessonDto(studyPlanId = studyPlan!!.id, title = it.substring(0, index), principleList = listOf(principle))
        }

        val liveStudyPlanList = this@StudyItemsViewModel.liveStudyLessonList as MutableLiveData
        liveStudyPlanList.postValue(studyPlanLessons)
    }
}

class StudyLesson(val id: Long = 0L,
                  var studyPlanId: Long = 0L,
                  var title: String = "",
                  var position: Int = 0)

class StudyPrinciple(val id: Long = 0L,
                     var studyLessonId: Long = 0L,
                     var title: String = "",
                     var subItemId: Long = 0L,
                     var contentUri: String = "",
                     var position: Int = 0) // position within the entire plan, not the lesson

class StudyPrincipleCompletion(var studyPrincipleId: Long = 0L,
                               var completionDate: LocalDate = LocalDate.now())

class StudyLessonDto(val id: Long = nextId++,
                     var studyPlanId: Long = 0L,
                     var title: String = "",
                     var principleList: List<StudyPrincipleDto>?) {
    init {
        principleList?.forEach {
            it.studyLessonId = id
        }
    }

    companion object {
        var nextId = 1L
    }
}

class StudyPrincipleDto(var id: Long = nextId++,
                        var studyLessonId: Long = 0L,
                        var title: String = "",
                        var subItemId: Long = 0L,
                        var contentUri: String = "",
                        var position: Int = 0,
                        var completionDate: LocalDate? = null) {
    fun isComplete() = completionDate != null

    companion object {
        var nextId = 1L
    }
}

package org.lds.ldssa.ux.study.plans

import android.app.Application
import android.arch.lifecycle.AndroidViewModel
import android.arch.lifecycle.LiveData
import org.threeten.bp.LocalDate
import javax.inject.Inject

class StudyPlansViewModel @Inject constructor(app: Application) : AndroidViewModel(app) {

    var selectedTab: StudyPlanTab = StudyPlanTab.DEFAULT_TAB
    var scrollPosition: Int = 0
    val featuredStudyPlans: LiveData<List<StudyPlanDto>> = ImmediateLiveData(featuredList)
    val myStudyPlans: LiveData<List<StudyPlanDto>> = ImmediateLiveData(myPlansList)

    //todo temporary database
    companion object {
        val bofmSubscription = StudyPlanSubscriptionDto(title = "Read the Book of Mormon in One Year", subscribedDate = LocalDate.of(2017, 2, 1), lastCompletedItem = 125)
        val cfmOtSubscription = StudyPlanSubscriptionDto(title = "Come Follow Me-Old Testament", subscribedDate = LocalDate.of(2017, 9, 27))
        val bofmPlan = StudyPlanDto(title = "Read the Book of Mormon", imageName = "book_of_mormon.png", pace = StudyPlanPace.USER_DRIVEN, totalLessons = 53, totalPrinciples = 365, subscription = bofmSubscription)
        val cfmOtPlan = StudyPlanDto(title = "Come Follow Me-Old Testament", imageName = "new_testament.png", contentItemId = 201395308L, pace = StudyPlanPace.TIME_DRIVEN, totalLessons = 16, totalPrinciples = 16, subscription = cfmOtSubscription)
        private val myPlansList = listOf(bofmPlan, cfmOtPlan)
        private val featuredList = listOf(bofmPlan, cfmOtPlan)
    }
}

//todo temporary classes until we have a real ones
class ImmediateLiveData<T>(value: T) : LiveData<T>() {
    init {
        postValue(value)
    }
}

enum class StudyPlanPace {
    TIME_DRIVEN, USER_DRIVEN
}

class StudyPlan(var id: Long = 0L,
                var title: String = "",
                var imageName: String? = null,
                var contentItemId: Long = 0L,
                var pace: StudyPlanPace = StudyPlanPace.TIME_DRIVEN)

class StudyPlanSubscription(var id: Long = 0L,
                            var studyPlanId: Long = 0L,
                            var title: String = "",
                            var subscribedDate: LocalDate = LocalDate.now())

class StudyPlanSubscriptionDto(val id: Long = nextId++,
                               var studyPlanId: Long = 0L,
                               var title: String = "",
                               var subscribedDate: LocalDate = LocalDate.now(),
                               var lastCompletedItem: Int = 0) {
    companion object {
        var nextId = 1L
    }
}

class StudyPlanDto(val id: Long = nextId++,
                   var title: String = "",
                   var imageName: String? = null,
                   var contentItemId: Long = 0L,
                   var totalLessons: Int = 1,
                   var totalPrinciples: Int = 1,
                   var pace: StudyPlanPace = StudyPlanPace.TIME_DRIVEN,
                   var subscription: StudyPlanSubscriptionDto? = null) {
    init {
        subscription?.studyPlanId = id
    }

    companion object {
        var nextId = 1L
    }
}

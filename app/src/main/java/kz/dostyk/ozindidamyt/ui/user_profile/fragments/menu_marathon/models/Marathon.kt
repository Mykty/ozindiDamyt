package kz.dostyk.ozindidamyt.ui.user_profile.fragments.menu_sport.models

data class Marathon(
        var title: String = "",
        var finishedDate: String = "",
        var earnedPoint: String = "") {
}

data class MarathonRating(
        var info: String = "",
        var rating: String = "",
        var point: String = "",
        var photo: String = "") {
}

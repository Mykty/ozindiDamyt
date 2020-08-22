package kz.dostyk.ozindidamyt.ui.user_profile.fragments.menu_sport.models

data class Sport(
        var name: String = "",
        var date: String = "",
        var time: String = "",) {
}


data class SportRating(
        var info: String = "",
        var rating: String = "",
        var point: String = "",
        var totalTime: String = "",
        var photo: String = "") {
}

package ru.teamfc.fancust.admin

enum class Role { //: GrantedAuthority
    GUEST,
    USER, // обычный авторизованный пользователь (как GUEST, но с ЛК)
    TEAMMATE, // просмотр контента, который загружают другие пользователи (мин статус человека из команды, будь то тренер, игрок или кто-то еще)
    MODERATOR, // просмотр всего контента, может менять контент сайта
    ADMIN; // может может менять контент на сайте

    companion object {
        fun getInheritedRoles(role: Role): List<Role> {
            return entries.toTypedArray().take(role.ordinal + 1)
        }
    }
}
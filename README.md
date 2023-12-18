# Emernots или же AmernotsApp
Приложение Emernots как часть обучающего проекта. \
Полное название Emergency notification system - мобильное приложение для быстрого оповещения о различных экстренны ситуациях.. 

Презентация проекта - https://disk.yandex.ru/i/sCL_sGT16BEa4Q

## Стек технологий:
mvvm, Kotlin Coroutines - https://github.com/DeM0NChiCk/Amernots_App/tree/master/app/src/main/java/com/example/amernotsapp/ui/vm \
Dagger 2 - https://github.com/DeM0NChiCk/Amernots_App/blob/master/app/src/main/java/com/example/amernotsapp/di/AppComponent.kt, https://github.com/DeM0NChiCk/Amernots_App/blob/master/app/src/main/java/com/example/amernotsapp/AmernotsAppAplication.kt, https://github.com/DeM0NChiCk/Amernots_App/blob/master/app/src/main/java/com/example/amernotsapp/di/AppBindsModule.kt, https://github.com/DeM0NChiCk/Amernots_App/blob/master/app/src/main/java/com/example/amernotsapp/di/DiExtensions.kt \
SharedPreferences - https://github.com/DeM0NChiCk/Amernots_App/blob/master/app/src/main/java/com/example/amernotsapp/ui/preferences/CredentialsPreferences.kt \
REST (Retrofit, OkHttp, Gson) - https://github.com/DeM0NChiCk/Amernots_App/tree/master/app/src/main/java/com/example/amernotsapp/data/api, https://github.com/DeM0NChiCk/Amernots_App/blob/master/app/src/main/java/com/example/amernotsapp/di/DataModule.kt \
Clean Architecture - https://github.com/DeM0NChiCk/Amernots_App/tree/master/app/src/main/java/com/example/amernotsapp \
androidx.preference - https://github.com/DeM0NChiCk/Amernots_App/blob/master/app/src/main/res/xml/settings.xml, https://github.com/DeM0NChiCk/Amernots_App/blob/master/app/src/main/java/com/example/amernotsapp/ui/screen/fragment/SettingsFragment.kt 

Для запуска потребуется такие api, как Amernots Api (https://github.com/DeM0NChiCk/AmernotsApi) и DaData (https://dadata.ru/api/suggest/address/#request).
Так как Amerntos Api работает только в localhost, то нужно менять IP адрес в build.gradle. \
Из особеностей поведения:
- только пользователь без профессии может создавать новости;
- пользователь, который выбрал профессию может отвечать на новости;
- предусмотрена смена темы на системную, светлую или тёмную;
- при смене пароля пользователю нужно снова авторизоваться;
- Токен Amernots Api хранится около часа в кэше, максимальное значение около 24ч;
- Подсказки от DaData работают подробно только в русской раскладке;
- Максимальное кол-во запросов к DaData 10000 запросов в сутки;
- Если полность выйти из приложения, то последнее состоянее сохранится;
- Если не использоваться AmernitsApi приложение в некоторых случаях будет реагировать на некоторые действия, но кроме ошибок в Toast вы ничего не увидитe)

Есть одно странное поведение смены темы. \
Если сменить пароль, затем перезайти на этот же аккаунт, то при смене темы вас снова перекинет на экран регистрации, но тема при это сменится и выйдет Toast о смене пароля.
Пока не выяснил почему так.

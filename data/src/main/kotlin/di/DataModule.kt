package di

fun dataModule() = listOf(
    ktorModule,
    roomModule,
    dataSourceModule,
    repositoryModule,
    networkMonitorModule
)
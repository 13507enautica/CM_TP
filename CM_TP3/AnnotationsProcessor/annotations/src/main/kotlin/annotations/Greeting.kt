package com.cmtp1.annotations

@Target ( AnnotationTarget.FUNCTION )
@Retention ( AnnotationRetention.SOURCE )
annotation class Greeting (val message : String )
package com.raka.repositorieslistgithub.util

/**Check if an event is handled or not
 * link to tutorial
 * https://medium.com/androiddevelopers/livedata-with-snackbar-navigation-and-other-events-the-singleliveevent-case-ac2622673150
 */
open class EventWrapper<out T>(private val content:T) {
    var hasBeenHandled = false
    private set

    fun getContentIfnotHandled():T?{
        return if(hasBeenHandled){
            null
        }else{
            hasBeenHandled = true
            content
        }
    }
    fun peekContent():T = content

}
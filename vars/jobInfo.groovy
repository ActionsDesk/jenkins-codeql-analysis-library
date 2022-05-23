String call() {
  /**
   * Provides the username of the user who kicked off the build
   *
   * @return The username of the person who kicked off the build
   */

  println('Getting user id...')
  return currentBuild.rawBuild.getCause(Cause.UserIdCause).getUserId()
}

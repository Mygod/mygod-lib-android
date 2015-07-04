package tk.mygod

package object concurrent {
  val FailureHandler: PartialFunction[Throwable, Unit] = { case exc => throw exc }
}

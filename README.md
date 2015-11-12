# Mygod Library for Android
This is the library where I collect reusable codes from my Android projects and reuse them for infinity times.

I'd say they are pretty useful, aren't they?

You don't want to use the whole library? Use ProGuard and `shrinkResources` to strip out useless things!

Issues/pull requests are welcome.

## Dependencies
* SBT
* Android support libraries (use SDK manager to install)

You should install the latest version unless this repo has been inactive for a long time. In that case, go to
 `build.sbt` for version.

## Building
    sbt clean android:packageAar

## Known issues
Whoever uses this library should include a bunch of code given in `build.sbt`.

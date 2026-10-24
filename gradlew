#!/usr/bin/env bash
# Gradle wrapper startup script.
# NOTE: the Gradle wrapper JAR (gradle/wrapper/gradle-wrapper.jar) must be present
# for this script to work. It is not committed in this environment because it is a
# binary artifact that could not be downloaded here. On GitHub Actions runners the
# wrapper is restored/bootstrapped by the CI workflow.
APP_ARGS="$(basename "$0")"

APP_HOME=$(cd "$(dirname "$0")" && pwd)/..

APP_HOME=$(cd "$APP_HOME" && pwd)

DEFAULT_JVM_OPTS='"-Xmx64m" "-Dfile.encoding=UTF-8"'

# Use the maximum available, or set MAX_FD != -1 to use that value.
MAX_FD="maximum"

warn() {
    echo "$*"
}

die() {
    echo
    echo "$*"
    echo
    exit 1
}

cygwin=false
msys=false
darwin=false
nonstop=false
case "$(uname)" in
    CYGWIN* ) cygwin=true ;;
    MINGW* ) msys=true ;;
    DARWIN* ) darwin=true ;;
    NONSTOP* ) nonstop=true ;;
esac

CLASSPATH=$APP_HOME/gradle/wrapper/gradle-wrapper.jar

if [ -x "$JAVA_HOME/bin/java" ]; then
    JAVACMD="$JAVA_HOME/bin/java"
elif [ -n "$JAVA_HOME" ] && [ -x "$JAVA_HOME/jre/sh/java" ]; then
    JAVACMD="$JAVA_HOME/jre/sh/java"
elif command -v java >/dev/null 2>&1; then
    JAVACMD=java
else
    die "ERROR: JAVA_HOME is not set and no 'java' command could be found in your PATH."
fi

if [ ! -x "$JAVACMD" ]; then
    die "ERROR: JAVA_HOME is set to an invalid directory: $JAVA_HOME"
fi

if [ ! -f "$CLASSPATH" ]; then
    die "ERROR: Gradle wrapper JAR not found at $CLASSPATH"
fi

exec "$JAVACMD" $DEFAULT_JVM_OPTS -classpath "$CLASSPATH" org.gradle.wrapper.GradleWrapperMain "$@"

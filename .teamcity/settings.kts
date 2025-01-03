import jetbrains.buildServer.configs.kotlin.*
import jetbrains.buildServer.configs.kotlin.buildFeatures.perfmon
import jetbrains.buildServer.configs.kotlin.buildSteps.maven
import jetbrains.buildServer.configs.kotlin.triggers.vcs
import jetbrains.buildServer.configs.kotlin.vcs.GitVcsRoot

/*
The settings script is an entry point for defining a TeamCity
project hierarchy. The script should contain a single call to the
project() function with a Project instance or an init function as
an argument.

VcsRoots, BuildTypes, Templates, and subprojects can be
registered inside the project using the vcsRoot(), buildType(),
template(), and subProject() methods respectively.

To debug settings scripts in command-line, run the

    mvnDebug org.jetbrains.teamcity:teamcity-configs-maven-plugin:generate

command and attach your debugger to the port 8000.

To debug in IntelliJ Idea, open the 'Maven Projects' tool window (View
-> Tool Windows -> Maven Projects), find the generate task node
(Plugins -> teamcity-configs -> teamcity-configs:generate), the
'Debug' option is available in the context menu for the task.
*/

version = "2024.12"

project {

    vcsRoot(GitGithubComDsgolovinExampleTeamcityGitRefsHeadsMaster1)

    buildType(Build)
}

object Build : BuildType({
    name = "Build"

    artifactRules = "target/*.jar"

    vcs {
        root(DslContext.settingsRoot)
    }

    steps {
        maven {
            id = "Maven2"

            conditions {
                contains("teamcity.build.branch", "master")
            }
            goals = "clean deploy"
            runnerArgs = "-Dmaven.test.failure.ignore=true"
            userSettingsSelection = "settings.xml"
        }
        maven {
            id = "Maven2_1"
            goals = "clean package"
        }
    }

    triggers {
        vcs {
        }
    }

    features {
        perfmon {
        }
    }
})

object GitGithubComDsgolovinExampleTeamcityGitRefsHeadsMaster1 : GitVcsRoot({
    name = "git@github.com:dsgolovin/example-teamcity.git#refs/heads/master (1)"
    url = "git@github.com:dsgolovin/example-teamcity.git"
    pushUrl = "git@github.com:dsgolovin/example-teamcity.git"
    branch = "refs/heads/master"
    branchSpec = "refs/heads/*"
    authMethod = uploadedKey {
        uploadedKey = "id_ed25519"
    }
})

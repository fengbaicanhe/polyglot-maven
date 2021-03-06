class Profiles {
    protected val profiles = mutableListOf<Profile>()
    operator fun component1(): List<Profile> = profiles

    fun profile(id: String, block: (@Scope Profile).() -> Unit) {
        val profile = Profile(id)
        profiles.add(profile)
        block(profile)
    }

    class Profile(val id: String = "default") {
        protected var thisBuild: Build? = null
            set(value) {
                check(thisBuild == null, { "Build is defined several times" })
                field = value
            }
        operator fun component1() = thisBuild

        fun build(block: (@Scope Build).() -> Unit) {
            thisBuild = Build()
            block(thisBuild!!)
        }


        protected var thisActivation: Activation? = null
            set(value) {
                check(thisActivation == null, { "Activation is defined several times" })
                field = value
            }
        operator fun component2() = thisActivation

        fun activation(default: Boolean = false, jdk: String? = null, block: (@Scope Activation).() -> Unit) {
            thisActivation = Activation(default, jdk)
            block(thisActivation!!)
        }

        protected var thisDependencies: Dependencies? = null
            set(value) {
                check(thisDependencies == null, { "Profile dependencies is defined several times" })
                field = value
            }
        operator fun component3() = thisDependencies
        fun dependencies(block: (@Scope Dependencies).() -> Unit) {
            thisDependencies = Dependencies()
            block(thisDependencies!!)
        }

        protected var thisDependencyManagement: DependencyManagement? = null
            set(value) {
                check(thisDependencyManagement == null, { "Profile dependencyManagement is defined several times" })
                field = value
            }
        operator fun component4() = thisDependencyManagement
        fun dependencyManagement(block: (@Scope DependencyManagement).() -> Unit) {
            thisDependencyManagement = DependencyManagement(Dependencies())
            block(thisDependencyManagement!!)
        }
    }
}
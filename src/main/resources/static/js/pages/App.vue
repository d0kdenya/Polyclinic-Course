<template>
  <v-app>
    <v-toolbar app>
      <v-toolbar-title>Polyclinic</v-toolbar-title>
      <v-btn
          flat
          v-if="profile"
          :disabled="$route.path === '/'"
          @click="showPatients"
      >
        All patients
      </v-btn>
      <v-btn
          flat
          v-if="profile"
          :disabled="$route.path === '/dep'"
          @click="showDepartments"
      >
        All departments
      </v-btn>
      <v-btn
          flat
          v-if="profile"
          :disabled="$route.path === '/freeDep'"
          @click="showFreeDepartments"
      >
        Free departments
      </v-btn>
      <v-btn
          flat
          v-if="profile"
          :disabled="$route.path === '/depPat'"
          @click="showDepartmentPatients"
      >
        Department patients
      </v-btn>
      <v-btn
          flat
          v-if="profile"
          :disabled="$route.path === '/tree'"
          @click="showSubordinateDepartments"
      >
        Subordinate departments
      </v-btn>
      <v-spacer></v-spacer>
      <v-btn
          flat
          v-if="profile"
          :disabled="$route.path === '/profile'"
          @click="showProfile"
      >
        {{profile.name}}
      </v-btn>
      <v-btn v-if="profile" icon href="/logout">
        <img class="img-exit" src="https://cdn-icons-png.flaticon.com/128/603/603018.png" alt="">
      </v-btn>
    </v-toolbar>
    <v-content>
      <router-view></router-view>
    </v-content>
  </v-app>
</template>

<script>
import { mapState, mapMutations } from 'vuex'
import { addHandler } from 'util/ws'

export default {
  computed: mapState(['profile']),
  methods: {
    ...mapMutations([
                   'addPatientMutations',
                   'updatePatientMutations',
                   'removePatientMutations'
    ]),
    showPatients() {
      this.$router.push('/')
    },
    showDepartments() {
      this.$router.push('/dep')
    },
    showFreeDepartments() {
      this.$router.push('/freeDep')
    },
    showDepartmentPatients() {
      this.$router.push('/depPat')
    },
    showSubordinateDepartments() {
      this.$router.push('/tree')
    },
    showProfile() {
      this.$router.push('/profile')
    }
  },
  created() {
    addHandler(data => {
      if (data.objectType === 'PATIENT') {
        switch (data.eventType) {
          case 'CREATE':
            this.addPatientMutations(data.body)
            break
          case 'UPDATE':
            this.updatePatientMutations(data.body)
            break
          case 'REMOVE':
            this.removePatientMutations(data.body)
            break
          default:
            console.error(`Looks like the event type if unknown "${data.eventType}"`)
        }
      } else {
        console.error(`Looks like the object type if unknown "${data.objectType}"`)
      }
    })
  },
  beforeMount() {
    if (!this.profile) {
      this.$router.replace('/auth')
    }
  }
}
</script>

<style>
.img-exit {
  width: 30px;
  height: 30px;
}
</style>

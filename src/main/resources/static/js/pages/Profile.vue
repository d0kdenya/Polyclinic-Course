<template>
  <v-container>
    <v-layout justify-space-around>
      <v-flex xs6>
        <div class="title mb-3">Профиль</div>
        <v-layout row justify-space-between>
          <v-flex class="px-1">
            <v-img :src="profile.userpic"></v-img>
          </v-flex>
          <v-flex  class="px-1">
            <v-layout column>
              <v-flex>Имя пользователя: {{ profile.name }}</v-flex>
              <v-flex>Регион: {{ profile.locale }}</v-flex>
              <v-flex v-if="profile.gender">Пол: {{ profile.gender }}</v-flex>
              <v-flex>Почта: {{ profile.email }}</v-flex>
              <v-flex>Последний визит: {{ profile.lastVisit }}</v-flex>
            </v-layout>
          </v-flex>
        </v-layout>
      </v-flex>
    </v-layout>
  </v-container>
</template>

<script>
import { mapState } from "vuex";
export default {
  name: "Profile",
  computed: mapState(['profile'])
}
</script>

<style scoped>
  img {
    max-width: 100%;
    height: auto;
  }
</style>
<template>
  <v-card class="my-2">
    <v-card-text primary-title>
      <i>({{ patient.id }})</i>
      ФИО: {{ patient.lastname }}  {{ patient.name }}  {{ patient.patronymic }}
      <div v-if="patient.dateOfBirth">Дата рождения: {{ patient.dateOfBirth }}</div>
      <div v-if="patient.department">Отделение: {{ patient.department.name }} </div>
      <div>Дата создания: {{ patient.creationDate }}</div>
    </v-card-text>
    <v-card-actions>
      <v-btn @click="edit" small flat round>Edit</v-btn>
      <v-btn icon @click="del" small>
        <img class="img-delete" src="http://cdn.onlinewebfonts.com/svg/img_275374.png" alt="Удалить">
      </v-btn>
    </v-card-actions>
  </v-card>
</template>

<script>
import { mapActions } from 'vuex'
export default {
  name: 'patient-row',
  props: ['patient', 'editPatient'],
  methods: {
    ...mapActions(['removePatientAction']),
    edit() {
      this.editPatient(this.patient)
    },
    del() {
      this.removePatientAction(this.patient)
    }
  }
}
</script>

<style>
  .img-delete {
    width: 100%;
    height: 100%;
  }

  .btn {
    padding: 10px 15px;
    background: none;
    color: teal;
    border: 1px solid teal;
  }
</style>

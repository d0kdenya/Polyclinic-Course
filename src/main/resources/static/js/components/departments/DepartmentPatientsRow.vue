<template>
  <v-card class="my-2">
    <v-card-text v-if="this.select.id" primary-title>
      <i>({{ patient.id }})</i>
      ФИО: {{ patient.lastname }}  {{ patient.name }}  {{ patient.patronymic }}
      <div v-if="patient.dateOfBirth">Дата рождения: {{ patient.dateOfBirth }}</div>
      <div v-if="patient.department">Отделение: {{ patient.department.name }} </div>
      <div>Дата создания: {{ patient.creationDate }}</div>
    </v-card-text>
  </v-card>
</template>

<script>
export default {
  name: 'department-patients-row',
  props: ['patient', 'select'],
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

<template>
  <v-layout row>
    <v-text-field
        label="Фамилия*"
        placeholder="Введите фамлиию пациента"
        v-model="lastname"
    />
    <v-text-field
        label="Имя*"
        placeholder="Введите имя пациента"
        v-model="name"
    />
    <v-text-field
        label="Отчество*"
        placeholder="Введите отчество пациента"
        v-model="patronymic"
    />
    <v-text-field
        label="Дата рождения"
        placeholder="Введите дату рождения пациента"
        type="date"
        v-model="dateOfBirth"
    />
    <v-select
        v-model="select"
        :items="this.departments"
        item-text="name"
        item-value="id"
        label="Выберите отделение*"
        persistent-hint
        return-object
        single-line
    ></v-select>
    <v-btn @click="save">
      Save
    </v-btn>
  </v-layout>
</template>

<script>
import { mapActions } from 'vuex'

export default {
  name: 'patient-form',
  props: ['patientAttr', 'departments'],
  data() {
    return {
      select: {id: null, name: ''},
      patronymic: '',
      department: {},
      dateOfBirth: null,
      lastname: '',
      name: '',
      id: ''
    }
  },
  watch: {
    patientAttr(newVal, oldVal) {
      this.creationDate = newVal.creationDate
      this.patronymic = newVal.patronymic
      this.dateOfBirth = newVal.dateOfBirth
      this.lastname = newVal.lastname
      this.select = newVal.select
      this.department = newVal.department
      this.name = newVal.name
      this.id = newVal.id
    }
  },
  methods: {
    ...mapActions(['updatePatientAction']),
    save() {
      const patient = {
        id: this.id,
        name: this.name,
        lastname: this.lastname,
        patronymic: this.patronymic,
        dateOfBirth: this.dateOfBirth,
        creationDate: this.creationDate,
        department: this.select
      }
      if (this.id) {
        this.updatePatientAction(patient)
      }
      this.patronymic = ''
      this.dateOfBirth = null
      this.lastname = ''
      this.select = {id: null, name: ''}
      this.department = {}
      this.name = ''
      this.id = ''
    }
  }
}
</script>

<style>

</style>

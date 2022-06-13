<template>
  <v-layout row>
    <v-text-field
        label="Наименование*"
        placeholder="Введите наименование отделения"
        v-model="name"
    />
    <v-text-field
        label="Адрес*"
        placeholder="Введите адрес отделения"
        v-model="address"
    />
    <v-text-field
        label="Вместимость*"
        placeholder="Введите вместимость отделения"
        v-model="limitOfPatients"
    />
    <v-btn @click="save">
      Save
    </v-btn>
  </v-layout>
</template>

<script>
import { mapActions } from 'vuex'

export default {
  name: 'department-form',
  props: ['departmentAttr'],
  data() {
    return {
      address: '',
      limitOfPatients: 0,
      countOfPatients: 0,
      name: '',
      id: '',
    }
  },
  watch: {
    departmentAttr(newVal, oldVal) {
      this.creationDate = newVal.creationDate
      this.name = newVal.name
      this.id = newVal.id
      this.address = newVal.address
      this.limitOfPatients = newVal.limitOfPatients
      this.countOfPatients = newVal.countOfPatients
      this.parentId = newVal.parentId
    }
  },
  methods: {
    ...mapActions(['updateDepartmentAction']),
    save() {
      const department = {
        id: this.id,
        name: this.name,
        address: this.address,
        limitOfPatients: this.limitOfPatients,
        countOfPatients: this.countOfPatients,
        parentId: this.parentId,
        creationDate: this.creationDate
      }
      if (this.id) {
        this.updateDepartmentAction(department)
      }
      this.name = ''
      this.id = ''
      this.address = ''
      this.limitOfPatients = 0
      this.countOfPatients = 0
    }
  }
}
</script>

<style>

</style>

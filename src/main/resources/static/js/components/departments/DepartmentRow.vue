<template>
  <v-card class="my-2">
    <v-card-text primary-title>
      <i>({{ department.id }})</i>
      Наименование: {{ department.name }}
      <div>Адрес: {{ department.address }}</div>
      <div>Состояние отделения: {{ department.countOfPatients }} / {{ department.limitOfPatients }}</div>
      <div v-if="department.parentId !== 0">Подчиняется: {{ department.parentId }}</div>
      <div>Дата создания: {{ department.creationDate }}</div>
    </v-card-text>
    <v-card-actions v-if="!isFreeDepartments">
      <v-btn @click="edit" small flat round>Edit</v-btn>
      <v-btn icon @click="del" small type="submit">
        <img class="img-delete" src="http://cdn.onlinewebfonts.com/svg/img_275374.png" alt="Удалить">
      </v-btn>
    </v-card-actions>
  </v-card>
</template>

<script>
import { mapActions } from 'vuex'
export default {
  name: 'department-row',
  props: ['department', 'editDepartment', 'isFreeDepartments'],
  methods: {
    ...mapActions(['removeDepartmentAction']),
    edit() {
      this.editDepartment(this.department)
    },
    del() {
      this.removeDepartmentAction(this.department)
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

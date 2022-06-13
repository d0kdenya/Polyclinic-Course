<template>
  <v-container>
    <v-layout align-space-around justify-start column>
      <h1>Все отделения</h1>
      <department-dialog
        :sortedDepartments="sortedDepartments"
      ></department-dialog>
      <department-form :departmentAttr="department">
      </department-form>
      <transition-group name="department-list">
        <department-row
            v-for="department in sortedDepartments"
            :key="department.id"
            :department="department"
            :editDepartment="editDepartment"
            :isFreeDepartments="false"
        />
      </transition-group>
    </v-layout>
  </v-container>
</template>

<script>
import { mapGetters } from 'vuex'
import DepartmentRow from '../components/departments/DepartmentRow.vue'
import DepartmentForm from '../components/departments/DepartmentForm.vue'
import DepartmentDialog from '../components/departments/DepartmentDialog.vue'


export default {
  components: {
    DepartmentRow,
    DepartmentForm,
    DepartmentDialog
  },
  data() {
    return {
      department: null
    }
  },
  computed: mapGetters(['sortedDepartments']),
  methods: {
    editDepartment(department) {
      this.department = department
    }
  }
}
</script>

<style>
.department-list-item {
  display: inline-block;
  margin-right: 10px;
}
.department-list-enter-active,
.department-list-leave-active {
  transition: all 0.4s ease;
}
.department-list-enter-from,
.department-list-leave-to {
  opacity: 0;
  transform: translateX(130px);
}
.department-list-move {
  transition: transform 0.4s ease;
}
</style>

<template>
  <v-container>
    <v-layout align-space-around justify-start column>
      <h1>Свободные отделения</h1>
      <transition-group name="department-list">
        <department-row
            v-for="department in freeDepartments"
            :key="department.id"
            :department="department"
            :isFreeDepartments="true"
        />
      </transition-group>
    </v-layout>
  </v-container>
</template>

<script>
import { mapGetters } from 'vuex'
import DepartmentRow from '../components/departments/DepartmentRow.vue'


export default {
  components: {
    DepartmentRow
  },
  data() {
    return {
      department: null
    }
  },
  computed: mapGetters(['freeDepartments']),
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

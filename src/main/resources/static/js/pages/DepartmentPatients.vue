<template>
  <v-container>
    <v-layout align-space-around justify-start column>
      <h1>Пациенты отделения</h1>
      <v-select
          v-model="select"
          :items="sortedDepartments"
          item-text="name"
          item-value="id"
          label="Выберите отделение"
          persistent-hint
          return-object
          single-line
      ></v-select>
      <transition-group name="patient-list">
        <department-patients-row
            v-for="patient in this.findDepartmentPatients[this.select.id]"
            :key="patient.id"
            :patient="patient"
            :select="select"
        />
      </transition-group>
    </v-layout>
  </v-container>
</template>

<script>
import { mapGetters } from 'vuex'
import DepartmentPatientsRow from '../components/departments/DepartmentPatientsRow.vue'


export default {
  components: {
    DepartmentPatientsRow
  },
  data() {
    return {
      select: {id: null, name: ''},
      patient: null,
      departments: null
    }
  },
  computed: mapGetters(['sortedPatients', 'sortedDepartments', 'findDepartmentPatients']),

}
</script>

<style>
  .patient-list-item {
    display: inline-block;
    margin-right: 10px;
  }
  .patient-list-enter-active,
  .patient-list-leave-active {
    transition: all 0.4s ease;
  }
  .patient-list-enter-from,
  .patient-list-leave-to {
    opacity: 0;
    transform: translateX(130px);
  }
  .patient-list-move {
    transition: transform 0.4s ease;
  }
</style>

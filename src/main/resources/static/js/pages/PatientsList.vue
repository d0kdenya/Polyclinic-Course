<template>
  <v-container>
    <v-layout align-space-around justify-start column>
      <h1>Все пациенты</h1>
      <patient-dialog :departments="freeDepartments"></patient-dialog>
      <patient-form
          :patientAttr="patient"
          :departments="freeDepartments"
      >
      </patient-form>
      <transition-group name="patient-list">
        <patient-row
            v-for="patient in sortedPatients"
            :key="patient.id"
            :patient="patient"
            :editPatient="editPatient"
        />
      </transition-group>
    </v-layout>
  </v-container>
</template>

<script>
import { mapGetters } from 'vuex'
import PatientRow from '../components/patients/PatientRow.vue'
import PatientForm from '../components/patients/PatientForm.vue'
import PatientDialog from '../components/patients/PatientDialog.vue'


export default {
  components: {
    PatientRow,
    PatientForm,
    PatientDialog
  },
  data() {
    return {
      patient: null,
      departments: null
    }
  },
  computed: mapGetters(['sortedPatients', 'freeDepartments']),
  methods: {
    editPatient(patient) {
      this.patient = patient
    }
  }
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

import Vue from 'vue'

const patients = Vue.resource('/patients{/id}')

export default {
    add: (patient) => patients.save({}, patient),
    update: patient => patients.update({id: patient.id}, patient),
    remove: id => patients.remove({id})
}
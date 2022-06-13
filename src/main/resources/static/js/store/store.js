import Vue from 'vue'
import Vuex from 'vuex'
import patientsApi from "../api/patients";
import departmentsApi from '../api/departments'
import Tree from '../util/tree'
import {logPlugin} from "@babel/preset-env/lib/debug";

Vue.use(Vuex)

export default new Vuex.Store({
    state: {
        departments: frontendData.departments,
        patients: frontendData.patients,
        profile: frontendData.profile
    },
    getters: {
        sortedPatients: state => (state.patients || []).sort((a, b) => -(a.id - b.id)),
        sortedDepartments: state => (state.departments || []).sort((a, b) => -(a.id - b.id)),
        freeDepartments: state => {
            const freeDep = (state.departments || []).sort((a, b) => -(a.id - b.id))
            return freeDep.filter(department => department.countOfPatients < department.limitOfPatients)
        },
        findDepartmentPatients: state => {
            let depPart = {}
            state.departments.forEach(department => {
                depPart[department.id] = state.patients.filter(patient => patient.department.id === department.id)
            })
            return depPart
        },
        getDepartmentState: state => {
            return state.departments
        },
        getTree: state => {

            const sortList = (state.departments || []).sort((a, b) => (a.id - b.id))

            let tree = new Tree(`${sortList[0].id}`, `${sortList[0].name}`)

            for (let i = 1; i < sortList.length; i++) {
                let parentId = sortList[i].parentId
                let j = 0

                while (sortList[j].id !== parentId) {
                    j++
                }

                tree.add(`${sortList[i].id}`, `${sortList[i].name}`, `${sortList[j].name}`, tree.traverseDF)
            }

            return tree._root
        }
    },
    // Мутации - синхронные вызовы
    mutations: {
        // Patients
        addPatientMutations(state, patient) {
            state.patients = [
                ...state.patients,
                patient
            ]
        },
        updatePatientMutations(state, patient) {
            const updateIndex = state.patients.findIndex(item => item.id === patient.id)
            state.patients = [
                ...state.patients.slice(0, updateIndex),
                patient,
                ...state.patients.slice(updateIndex + 1)
            ]
        },
        removePatientMutations(state, patient) {
            const deletionIndex = state.patients.findIndex(item => item.id === patient.id)

            if (deletionIndex > -1) {
                state.patients = [
                    ...state.patients.slice(0, deletionIndex),
                    ...state.patients.slice(deletionIndex + 1)
                ]
            }
        },

        // Departments
        addDepartmentMutations(state, department) {
            state.departments = [
                ...state.departments,
                department
            ]
        },
        updateDepartmentMutations(state, department) {
            const updateIndex = state.departments.findIndex(item => item.id === department.id)
            state.departments = [
                ...state.departments.slice(0, updateIndex),
                department,
                ...state.departments.slice(updateIndex + 1)
            ]
        },
        removeDepartmentMutations(state, department) {
            const deletionIndex = state.departments.findIndex(item => item.id === department.id)

            if (deletionIndex > -1) {
                state.departments = [
                    ...state.departments.slice(0, deletionIndex),
                    ...state.departments.slice(deletionIndex + 1)
                ]
            }
        }
    },
    // Экшены - ассинхронные действия
    actions: {
        // Patients
        async addPatientAction({commit, state}, patient) {
            const result = await patientsApi.add(patient)
            const data = await result.json()
            const index = state.patients.findIndex(item => item.id === data.id)

            if (index > -1) {
                commit('updatePatientMutations', data)
            } else {
                commit('addPatientMutations', data)
            }
        },
        async updatePatientAction({commit}, patient) {
            const result = await patientsApi.update(patient)
            const data = await result.json()

            commit('updatePatientMutations', data)
        },
        async removePatientAction({commit}, patient) {
            const result = await patientsApi.remove(patient.id)

            if (result.ok) {
                commit('removePatientMutations', patient)
            }
        },

        // Departments
        async addDepartmentAction({commit, state}, department) {
            const result = await departmentsApi.add(department)
            const data = await result.json()
            const index = state.departments.findIndex(item => item.id === data.id)

            if (index > -1) {
                commit('updateDepartmentMutations', data)
            } else {
                commit('addDepartmentMutations', data)
            }
        },
        async updateDepartmentAction({commit}, department) {
            const result = await departmentsApi.update(department)
            const data = await result.json()

            commit('updateDepartmentMutations', data)
        },
        async removeDepartmentAction({commit}, department) {
            const result = await departmentsApi.remove(department.id)

            if (result.ok) {
                commit('removeDepartmentMutations', department)
            }
        }
    }
})
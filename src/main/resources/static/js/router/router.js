import Vue from 'vue'
import VueRouter from 'vue-router'
import PatientsList from 'pages/PatientsList.vue'
import Auth from 'pages/Auth.vue'
import Profile from 'pages/Profile.vue'
import DepartmentsList from 'pages/DepartmentsList.vue'
import FreeDepartmentsList from 'pages/FreeDepartmentsList.vue'
import DepartmentPatients from 'pages/DepartmentPatients.vue'
import SubordinateDepartments from 'pages/SubordinateDepartments.vue'

Vue.use(VueRouter)

const routes = [
    { path: '/', component: PatientsList },
    { path: '/dep', component: DepartmentsList },
    { path: '/freeDep', component: FreeDepartmentsList },
    { path: '/depPat', component: DepartmentPatients },
    { path: '/tree', component: SubordinateDepartments },
    { path: '/auth', component: Auth },
    { path: '/profile', component: Profile },
    { path: '*', component: PatientsList },
]

export default new VueRouter({
    // Убираем #
    mode: 'history',
    routes
})
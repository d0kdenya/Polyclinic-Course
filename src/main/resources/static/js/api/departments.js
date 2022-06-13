import Vue from 'vue'

const departments = Vue.resource('/departments{/id}')

export default {
    add: department => departments.save({}, department),
    update: department => departments.update({id: department.id}, department),
    remove: id => departments.remove({id})
}
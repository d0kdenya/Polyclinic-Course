<template>
  <v-card class="my-2" style="border-radius: 10px" color="#ccdcef">
    <v-card-text primary-title>
      <li class="node-tree">
        <i>({{ currentDep.id }})</i>
        <span class="label">Наименование: {{ node.name }}</span>
        <div>Адрес: {{ currentDep.address }}</div>
        <div>Состояние отделения: {{ currentDep.countOfPatients }} / {{ currentDep.limitOfPatients }}</div>
        <div>Дата создания: {{ currentDep.creationDate }}</div>
        <ul v-if="node.children && node.children.length">
          <node v-for="child in node.children" :node="child"></node>
        </ul>
      </li>
    </v-card-text>
  </v-card>
</template>

<script>

export default {
  data() {
    return {
      currentDep: this.currentDepartment()
    }
  },

  name: 'node',
  props: {
    node: Object
  },
  methods: {
    currentDepartment() {
      let currentDep
      const departments = this.$store.getters.getDepartmentState
      for (let i = 0; i < departments.length; i++) {
        if (departments[i].name === this.node.name) {
          currentDep = departments[i]
        }
      }

      return currentDep
    }
  }
};
</script>

<style>
</style>
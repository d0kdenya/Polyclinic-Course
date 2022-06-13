<template>
  <div>
    <tree :tree-data="getTree"></tree>
  </div>
</template>

<script>
import Tree from './Tree.vue'
import {mapGetters} from "vuex";

export default {
  components: {
    Tree
  },
  computed: mapGetters(['getTree']),
};
</script>
<template>
  <v-row justify="center">
    <v-dialog
        v-model="dialog"
        persistent
        max-width="600px"
    >
      <template v-slot:activator="{ on, attrs }">
        <v-btn
            class="btn"
            v-bind="attrs"
            v-on="on"
            style="align-self: flex-end"
        >
          Добавить отделение
        </v-btn>
      </template>
      <v-card>
        <v-card-title>
          <span class="text-h5">Новое отделение</span>
        </v-card-title>
        <v-card-text>
          <v-container>
            <v-row>
              <v-col
                  cols="12"
                  sm="6"
                  md="4"
              >
                <v-text-field
                    label="Наименование*"
                    hint="Введите наименование отделения"
                    required
                    :departmentAttr="department"
                    v-model="name"
                ></v-text-field>
              </v-col>
              <v-col
                  cols="12"
                  sm="6"
                  md="4"
              >
                <v-text-field
                    label="Адрес*"
                    hint="Введите адрес отделения"
                    required
                    :departmentAttr="department"
                    v-model="address"
                ></v-text-field>
              </v-col>
              <v-col
                  cols="12"
                  sm="6"
                  md="4"
              >
                <v-text-field
                    label="Вместимость*"
                    hint="Введите вместимость отделения"
                    required
                    min="1"
                    max="1000"
                    type="number"
                    :departmentAttr="department"
                    v-model="limitOfPatients"
                ></v-text-field>
              </v-col>
              <v-col
                  cols="12"
                  sm="6"
              >
                <v-select
                    v-model="select"
                    :items="this.sortedDepartments"
                    item-text="name"
                    item-value="id"
                    label="Выберите главенствующее отделение*"
                    persistent-hint
                    return-object
                    single-line
                ></v-select>
              </v-col>
            </v-row>
          </v-container>
          <small>*обязательные поля</small>
        </v-card-text>
        <v-card-actions>
          <v-spacer></v-spacer>
          <v-btn
              class="btn"
              text
              @click="dialog=false"
          >
            Отмена
          </v-btn>
          <v-btn
              class="btn"
              text
              @click="save"
          >
            Сохранить
          </v-btn>
        </v-card-actions>
      </v-card>
    </v-dialog>
  </v-row>
</template>

<script>
import { mapActions } from 'vuex'

export default {
  name: 'department-dialog',
  props: ['departmentAttr', 'sortedDepartments'],
  data() {
    return {
      select: {id: null, name: ''},
      department: null,
      dialog: false,
      address: '',
      limitOfPatients: 1,
      countOfPatients: 0,
      parentId: null,
      name: '',
      id: ''
    }
  },
  watch: {
    departmentAttr(newVal, oldVal) {
      this.name = newVal.name
      this.id = newVal.id
      this.address = newVal.address
      this.limitOfPatients = newVal.limitOfPatients
      this.countOfPatients = newVal.countOfPatients
      this.parentId = newVal.parentId
    }
  },
  methods: {
    ...mapActions(['addDepartmentAction', 'updateDepartmentAction']),
    save() {
      const department = {
        id: this.id,
        name: this.name,
        address: this.address,
        limitOfPatients: this.limitOfPatients,
        countOfPatients: this.countOfPatients,
        parentId: this.select.id
      }
      this.dialog = false
      if (this.id) {
        this.updateDepartmentAction(department)
        // window.location.reload()
      } else {
        this.addDepartmentAction(department)
      }
      this.select = {}
      this.name = ''
      this.id = ''
      this.address = ''
      this.limitOfPatients = 1
      this.countOfPatients = 0
    }
  }
}
</script>

<style scoped>
.btn {
  padding: 10px 15px;
  background: none;
  color: teal;
  border: 1px solid teal;
}
</style>
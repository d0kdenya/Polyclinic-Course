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
          Добавить пациента
        </v-btn>
      </template>
      <v-card>
        <v-card-title>
          <span class="text-h5">Новый пациент</span>
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
                    label="Фамилия*"
                    hint="Введите фамлиию пациента"
                    required
                    :patientAttr="patient"
                    v-model="lastname"
                ></v-text-field>
              </v-col>
              <v-col
                  cols="12"
                  sm="6"
                  md="4"
              >
                <v-text-field
                    label="Имя*"
                    hint="Введите имя пациента"
                    required
                    :patientAttr="patient"
                    v-model="name"
                ></v-text-field>
              </v-col>
              <v-col
                  cols="12"
                  sm="6"
                  md="4"
              >
                <v-text-field
                    label="Отчество"
                    placeholder="Введите отчество пациента"
                    persistent-hint
                    :patientAttr="patient"
                    v-model="patronymic"
                ></v-text-field>
              </v-col>
              <v-col
                  cols="12"
                  sm="6"
                  md="4"
              >
                <v-text-field
                    label="Дата рождения"
                    hint="Введите дату рождения пациента"
                    persistent-hint
                    :patientAttr="patient"
                    type="date"
                    v-model="dateOfBirth"
                ></v-text-field>
              </v-col>
              <v-col
                  cols="12"
                  sm="6"
              >
                <v-select
                    v-model="select"
                    :items="this.departments"
                    item-text="name"
                    item-value="id"
                    label="Выберите отделение*"
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
import departments from "../../api/departments";

export default {
  name: 'patient-dialog',
  props: ['patientAttr', 'departments'],
  data() {
    return {
      select: {id: null, name: ''},
      patient: null,
      dialog: false,
      lastname: '',
      name: '',
      patronymic: '',
      dateOfBirth: null,
      departmentId: null,
      id: '',
    }
  },
  watch: {
    patientAttr(newVal, oldVal) {
      this.lastname = newVal.lastname
      this.name = newVal.name
      this.patronymic = newVal.patronymic
      this.dateOfBirth = newVal.dateOfBirth
      this.departmentId = newVal.departmentId
      this.id = newVal.id
    }
  },
  methods: {
    ...mapActions(['addPatientAction', 'updatePatientAction']),
    save() {
      const patient = {
        id: this.id,
        lastname: this.lastname,
        name: this.name,
        patronymic: this.patronymic,
        dateOfBirth: this.dateOfBirth,
        department: this.select
      }
      this.dialog = false
      if (this.id) {
        this.updatePatientAction(patient)
      } else {
        this.addPatientAction(patient)
      }
      this.lastname = ''
      this.name = ''
      this.patronymic = ''
      this.dateOfBirth = null
      this.select = {}
      this.id = ''
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
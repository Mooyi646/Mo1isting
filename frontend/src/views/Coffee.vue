<template>
    <a-card hoverable style="width: 300px">
      <template #cover>
        <img
          alt="example"
          src := coffee.coffeePic
        />
      </template>
      <template #actions>
        <setting-outlined key="setting" />
        <edit-outlined key="edit" />
        <ellipsis-outlined key="ellipsis" />
      </template>
      <a-card-meta title :=coffee.coffeeName>
        <template #avatar>
          <a-avatar src :=coffee.coffeeShop />
        </template>
      </a-card-meta>
    </a-card>
  </template>

<script>
import request from '@/utils/request.js'

export default{
    name: 'Coffee',
    components: {

    },
    data(){
        return{
            coffee: {},
        }
    },
    methods: {
        load(){
            request.get("/coffee").then(res => {
                console.log(res)
            })
        }
    }
}
</script>
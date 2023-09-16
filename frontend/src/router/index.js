import { createRouter, createWebHistory } from 'vue-router'


const router = createRouter({
  history: createWebHistory(import.meta.env.BASE_URL),
  routes: [
    {
      path: '/',
      name: 'home',
      component: import('../views/HomeView.vue')
    },
    {
      path: '/coffee',
      name: 'coffee',
      component: () => import('../views/Coffee.vue')
    },
    {
      path: '/login',
      name: 'login',
      component: () => import('../views/user/Login.vue')
    },
    {
      path: '/register',
      name: 'register',
      component: () => import('../views/user/Register.vue')
    }
  ]
})

export default router

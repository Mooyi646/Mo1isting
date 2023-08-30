<template>
    <!--设置背景-->
    <div class="background">
      <div class="backgroundpic">
        <el-image src="/for_web/suica/AF004465_04.gif" />
      </div>
        <div class="login-card">
    <a-card title="Welcome to Mo1isting"  hoverable="true" :bordered="false" style="width:100%; height: 100%;">
      <div class="components-input-demo-presuffix">
    <a-input v-model:value="userName" placeholder="请输入用户名/手机号/邮箱">
      <template #prefix>
        <user-outlined />
      </template>
    </a-input>
    <br />
    <br />
    <br />
    <a-input-password v-model:value="userPassword" placeholder="请输入密码" >
      <template #prefix>
        <KeyOutlined />
      </template>
      </a-input-password>
      <br />
      <br />
      <br />
      <a-button type="primary">登  录</a-button>
  </div>
    </a-card>
  </div>
    </div>
    
    </template>
    
    <script> 
    import request from '@/utils/request'
    import {
      UserOutlined,KeyOutlined
} from '@ant-design/icons-vue';
    
    export default{
      components:{
        UserOutlined,
        KeyOutlined
      },
    data(){
        return{
            form:{
              account:'',
              password:'',
            },
            rules: {
            account: [
              {required: true, message: '用户名不为空', trigger: 'blur'},
            ],
            password: [
              {required: true, message: '密码不为空', trigger: 'blur'},
            ],
          },
        }
    },
    create(){
      sessionStorage.removeItem("user")
    },
    methods:{
        login () {
          
          this.$refs['form'].validate((valid) => {
            if (valid) {
    // 参数保存
    let formData = new FormData();
    formData.append('userName',this.form.account);
    formData.append('userPassword',this.form.password);
    //用户登录
              request.post('/user/login',formData
              ).then(res => {
                if (res.code == 20000) {
                  this.$message({
                    type: "success",
                    message: "登录成功"
                  })
                  sessionStorage.setItem("user", JSON.stringify(res.data.User))  // 缓存用户信息
                  // 登录成功的时候更新当前路由
                  this.$router.push("/")  //登录成功之后进行页面的跳转，跳转到主页
                } else {
                    this.$message({
                    type: "error",
                    message:res.message
                  })
                }
              })
              
            }})
    
        }
    }
    }
          
    </script>

<style>
.background{
  background: #e3d1d1;
  margin-top: 130px;
  margin-left: 100px; 
  width: 900px;
  height:400px;  /**宽高100%是为了图片铺满屏幕 */
  display: flex;
}
.backgroundpic{
  width:50%; 
  height: 100%;
  position: absolute;
}
.login-card{
  padding: 30px;
  width: 400px;
  margin-left: 53%;
  height: 400px;
}
</style>
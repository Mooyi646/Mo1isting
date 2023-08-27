<template>
    <!--设置背景-->
    <div class="background" style="width:100%;  
        height:100%;  /**宽高100%是为了图片铺满屏幕 */
        z-index:-1;
        position: absolute;">
        <el-image src="https://raw.githubusercontent.com/Mooyi646/ImageSaver/main/202308272149932.gif" width="100%" height="100%" alt="" />
    </div>
        <div style="width:45%;margin-left:53%;margin-top:90px">
            <va-card  stripe >
                <va-card-title><div style="margin:auto; width:100%">
            <el-image  style="width: 20%; height: 20%;vertical-align: middle;" src="/logos/1.png" />
            <span style="font-weight:bolder; font-size:large; color:#004BAC">在线教育系统</span>
        </div></va-card-title>
        <va-card-title>登录</va-card-title>
        <el-form ref="form" :model="form" :rules="rules">
        <va-card-content><div style="width:100%;margin-left:20%; font-size:small;color:#919494">用户名/邮箱/电话 </div></va-card-content>
        <va-card-content><el-form-item prop="account"><el-input style="width:210px;margin-left:20%" v-model="form.account" placeholder="请输入用户名/邮箱/电话" clearable></el-input></el-form-item></va-card-content>
        <va-card-content><span  style="width:100%;margin-left:20%;font-size:small;color:#919494">密码 </span></va-card-content>
        <va-card-content><el-form-item prop="password"><el-input style="width:210px;margin-left:20%" v-model="form.password" placeholder="请输入密码" clearable show-password></el-input></el-form-item></va-card-content>
        <va-card-content><div style="width:100%; margin-left:30%"><el-button color="#004BAC" @click="login">登录</el-button></div></va-card-content>
        <va-card-content><span style="font-size:small;color:#919494"><a href="/register">无账号？点击注册</a></span></va-card-content>
        </el-form>
            </va-card>
        </div>
    
    </template>
    
    <script> 
    import request from '@/utils/request'
    
    export default{
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
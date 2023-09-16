<template>
  <div class="background">
    <div class="backgroundpic">
      <el-image src="/for_web/suica/AF004465_04.gif" />
    </div>
    <div class="login-card">
      <a-card class="cardStyle" title="Welcome to Mo1isting" :headStyle="headStyle" hoverable="true" :bordered="false">
        <a-form :model="form" name="basicLogin" class="login-form" @finish="onFinish" @finishFailed="onFinishFailed">
          <a-form-item label="账号" name="account" :rules="[{ required: true, message: '请输入用户名/手机号/邮箱' }]">
            <a-input v-model:value="form.account" placeholder = '请输入用户名/手机号/邮箱' />
          </a-form-item>

          <a-form-item label="密码" name="userPassword" :rules="[{ required: true, message: '请输入密码' }]">
            <a-input-password v-model:value="form.userPassword" placeholder='请输入密码' />
          </a-form-item>

          <a-form-item>
            <a-form-item name="remember" no-style>
              <a-checkbox v-model:checked="form.remember">记住我</a-checkbox>
            </a-form-item>
            <a href="" class="forgetPassword"><u>忘记密码</u></a>
          </a-form-item>

          <a-form-item>
            <a-button :disabled="disabled" type="primary" html-type="submit" style="width:100%">登录</a-button>
            <br />
            <br />
            <a href="/register" class="register">无账号？点击注册</a>
          </a-form-item>
        </a-form>
      </a-card>
    </div>
  </div>
</template>
    
<script lang="ts" setup>
import { reactive, computed, setBlockTracking } from 'vue';
import request from '../../utils/request';
import {message} from 'ant-design-vue';
import { useRouter } from 'vue-router'

const router = useRouter()


interface Form {
  account: string;
  userPassword: string;
  remember: boolean;
}

const form = reactive<Form>({
  account: '',
  userPassword: '',
  remember: true,
});

var headStyle = {
  "font-size": "18px",
  "color": "black",
  "font-weight": "bolder"
};

// 登录
const onFinish = () => {
  request.post('/user/login',form).then(res => {
    if(res.code == 0){ 
      message.success('登录成功');
      router.push('/')
    }
    else{
      message.error(res.msg);
    }
  })
};

const onFinishFailed = (errorInfo: any) => {
  console.log('Failed:', errorInfo);
};

const disabled = computed(() => {
  return !(form.account && form.userPassword);
});


</script>

<style>
.background {
  background: #e3d1d1;
  margin-top: 130px;
  width: 75vw;
  min-width: 750px;
  height: 500px;
  display: flex;
}

.backgroundpic {
  width: 40%;
  height: 100%;
  position: absolute;
}


.login-card {
  padding: 30px;
  width: 50%;
  margin-left: 40%;
}

.cardStyle{
  padding-top: 20px;
  margin-left: auto;
  height: 440px;
  width: 400px;
}

.forgetPassword{
  float: right;
  color:rgba(0, 119, 255, 0.817);
}

.register{
  color:rgba(0, 119, 255, 0.817);
}

</style>
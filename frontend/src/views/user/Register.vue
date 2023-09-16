<template>
    <div class="background">
        <div class="backgroundpic">
            <el-image src="/for_web/suica/AF004465_07.gif" />
        </div>
        <div class="login-card">
            <a-card class="cardStyle" title="Welcome to Mo1isting" :headStyle="headStyle" hoverable="true" :bordered="false">
                <a-form ref="formRef" name="custom-validation" class="login-form" :model="formState" :rules="rules"
                     @finish="handleFinish" @validate="handleValidate" @finishFailed="handleFinishFailed">
                    <a-form-item label="账号" name="account" :rules="[{ required: true, message: '请输入用户名/手机号/邮箱' }]">
                        <a-input v-model:value="formState.account" placeholder='请输入用户名' />
                    </a-form-item>

                    <a-form-item has-feedback label="密码" name="pass">
                        <a-input v-model:value="formState.userPassword" type="password" autocomplete="off" placeholder='请输入密码' />
                    </a-form-item>

                    <a-form-item has-feedback label="确认" name="checkPass">
                        <a-input v-model:value="formState.checkPass" type="password" autocomplete="off" placeholder='请再次输入密码' />
                    </a-form-item>
                    <br />
                    <br />

                    <a-form-item>
                        <a-button type="primary" :disabled="disabled" html-type="submit" style="width:100%">注册</a-button>
                    </a-form-item>
                </a-form>
            </a-card>
        </div>
    </div>
</template>
      
<script lang="ts" setup>
import { reactive, ref, computed } from 'vue';
import request from '../../utils/request';
import type { Rule } from 'ant-design-vue/es/form';
import type { FormInstance } from 'ant-design-vue';
import {message} from 'ant-design-vue';
import { useRouter } from 'vue-router'

const router = useRouter()

var headStyle = {
    "font-size": "18px",
    "color": "black",
    "font-weight": "bolder"
};

interface FormState {
    account: string;
    userPassword: string;
    checkPass: string;
}
const formRef = ref<FormInstance>();
const formState = reactive<FormState>({
    account: '',
    userPassword: '',
    checkPass: '',
});

const disabled = computed(() => {
  return !(formState.account && formState.userPassword && formState.checkPass);
});

const validatePass = async (_rule: Rule, value: string) => {
    if (value === '') {
        return Promise.reject('请输入密码');
    } else {
        if (formState.checkPass !== '') {
            formRef.value.validateFields('checkPass');
        }
        return Promise.resolve();
    }
};
const validatePass2 = async (_rule: Rule, value: string) => {
    if (value === '') {
        return Promise.reject('请再次输入密码');
    } else if (value !== formState.userPassword) {
        return Promise.reject("两次密码不一致");
    } else {
        return Promise.resolve();
    }
};

const rules: Record<string, Rule[]> = {
    pass: [{ required: true, validator: validatePass, trigger: 'change' }],
    checkPass: [{ required: true,validator: validatePass2, trigger: 'change' }],
};

const handleFinish = () => {
    let formdata  = new FormData();
    formdata.append("userName",formState.account);
    formdata.append("userPassword",formState.userPassword);
    request.post('/user/register',formdata).then(res => {
    if(res.code == 0){ 
      message.success('注册成功');
      router.push('/login')
    }
    else{
      message.error(res.msg);
    }
  })
};
const handleFinishFailed = errors => {
    console.log(errors);
};
const handleValidate = (...args) => {
    console.log(args);
};
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

.cardStyle {
    padding-top: 20px;
    margin-left: auto;
    height: 440px;
    width: 400px;
}

.forgetPassword {
    float: right;
    color: rgba(0, 119, 255, 0.817);
}

.register {
    color: rgba(0, 119, 255, 0.817);
}
</style>
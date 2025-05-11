import axios from "axios";
// 请求 api 的基础路径
export const API_BASE_URL = "http://localhost:8080/CETBoot/api";

// 设置请求类型为 form-data
axios.defaults.headers.post["Content-Type"] = "multipart/form-data";

export default function ({
                             url,
                             method,
                             data,
                             headers = {},
                             isLogin = false,
                             params = {}
                         }) {
    return new Promise((resolve, reject) => {
        // 从localStorage中获取token
        const token = localStorage.getItem("token");
        // 如果没有 token 则跳转到登录页面
        if (!token && !isLogin) {
            reject("no login");
            window.location.href = "/login";
        }
        axios({
            url: API_BASE_URL + url,
            method: method,
            data: data,
            headers: {
                "Authorization": `Bearer ${token}`,
                ...headers
            },
            params: params,
        }).then((response) => {
            const data = response.data;
            if (data.code !== 200) {
                // 如果请求失败，则返回错误信息
                reject(data.message);
            }
            resolve(data);
        }).catch((error) => {
            // 如果 token 过期，则跳转到登录页面
            if (error.status === 401) {
                localStorage.removeItem("token");
                window.location.href = "/login";
            }
            reject(error.response.data.message);
        });
    });
}
import request from "@/apis/request";

export function getPractices(current, size, keyword = "") {
    return request({
        url: "/listening/getPractices",
        method: "get",
        params: {
            current: current,
            size: size,
            keyword: keyword
        },
        isLogin: true
    });
}

export function getPractice(listeningPracticeId) {
    return request({
        url: "/listening/getPractice",
        method: "get",
        params: {
            listeningPracticeId: listeningPracticeId
        }
    });
}

export function submit(data) {
    return request({
        url: "/listening/submit",
        method: "post",
        data: data,
        headers: {
            "Content-Type": "application/json"
        }
    });
}

export function getRecords(current, size) {
    return request({
        url: "/listening/getRecords",
        method: "get",
        params: {
            current: current,
            size: size
        }
    });
}

export function getRecord(listeningRecordId) {
    return request({
        url: "/listening/getRecord",
        method: "get",
        params: {
            listeningRecordId: listeningRecordId
        }
    });
}

export function add(title, audio, exercises) {
    return request({
        url: "/listening/add",
        method: "post",
        data: {
            title: title,
            audio: audio,
            exercises: exercises
        },
        headers: {
            "Content-Type": "application/json"
        }
    })
}
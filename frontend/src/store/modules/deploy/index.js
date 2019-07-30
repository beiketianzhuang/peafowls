import axios from 'axios'
const deployStore = {
    state: {
        // jobInfos:[]
    },
    getters: {
        // jobInfos: state => state.jobInfos

    },
    mutations: {
        // setJobInfos(state, payload){
        //     state.jobInfos = payload;
        // }
    },
    actions: {
        // getJobInfos({commit}){
        //     axios.get("/scheduler/jobs",{withCredentials:false}).then(data=>{
        //         let res=data.data;
        //         commit("setJobInfos",res)
        //     }).catch((error)=>{
        //         console.log(error);
        //     })
        // }
    },
};

export default deployStore
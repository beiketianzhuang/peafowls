import Vue from 'vue'
import Vuex from 'vuex'

Vue.use(Vuex);

import deployStore from './modules/deploy'

const store = new Vuex.Store({
    modules:{
        deployStore,
    }
});
export default store;
import BuildDeploy from '../components/BuildDeploy';
import ContextList from '../components/ContextList';
import ContextMonitor from '../components/ContextMonitor';
import ApiManager from '../components/ApiManager';
import ApiDetails from '../components/ApiDetails';
import ContextEdit from '../components/ContextEdit';

const routers = [{
    path: '/',
    component: ContextList
}, {
    path: '/deploy',
    component: BuildDeploy
}, {
    path: '/monitor',
    component: ContextMonitor
}, {
    path: '/apiManager',
    component: ApiManager
}, {
    path: '/apiDetails',
    component: ApiDetails
}, {
    path: '/contextEdit',
    component: ContextEdit
}];
export default routers;
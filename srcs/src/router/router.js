import BuildDeploy from '../components/BuildDeploy';
import ContextList from '../components/ContextList';
import ContextMonitor from '../components/ContextMonitor';
const routers = [{
    path: '/',
    component: ContextList
},{
    path: '/deploy',
    component: BuildDeploy
},{
    path: '/monitor',
    component: ContextMonitor
}];
export default routers;
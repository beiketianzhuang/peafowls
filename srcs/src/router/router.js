import BuildDeploy from '../components/BuildDeploy';
import ContextList from '../components/ContextList';

const routers = [{
    path: '/',
    component: ContextList
},{
    path: '/deploy',
    component: BuildDeploy
}];
export default routers;
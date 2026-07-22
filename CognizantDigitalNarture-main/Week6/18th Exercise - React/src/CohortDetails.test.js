import { mount, shallow } from 'enzyme';
import toJson from 'enzyme-to-json';
import CohortDetails from './CohortDetails';
import { CohortsData } from './Cohort';

describe('Cohort Details Component', () => {

  test('should create the component', () => {
    const wrapper = shallow(<CohortDetails cohort={CohortsData[0]} />);
    expect(wrapper.exists()).toBe(true);
  });

  test('should initialize the props', () => {
    const cohort = CohortsData[0];
    const wrapper = mount(<CohortDetails cohort={cohort} />);
    expect(wrapper.props().cohort).toEqual(cohort);
  });

  test('should display cohort code in h3', () => {
    const cohort = CohortsData[0];
    const wrapper = mount(<CohortDetails cohort={cohort} />);
    const h3 = wrapper.find('h3');
    expect(h3.text()).toContain(cohort.cohortCode);
  });

  test('should always render same html', () => {
    const wrapper = shallow(<CohortDetails cohort={CohortsData[0]} />);
    expect(toJson(wrapper)).toMatchSnapshot();
  });

});

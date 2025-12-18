package vben.setup.bpm.bus;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import vben.setup.bpm.proc.BpmProcDefEntity;
import vben.setup.bpm.proc.BpmProcDefRepo;

/**
 * 流程初始化
 */
@Service
@RequiredArgsConstructor
@Transactional
public class BpmBusInitService {

    public void init() {
        BpmBusCateEntity cate = new BpmBusCateEntity();
        cate.setId(1L);
        cate.setName("人事管理");
        cate.setAvtag(true);
        cate.setOrnum(1);
        cate.setUptim(cate.getCrtim());
        cateRepo.save(cate);

        BpmBusCateEntity cate2 = new BpmBusCateEntity();
        cate2.setId(2L);
        cate2.setName("财务管理");
        cate2.setAvtag(true);
        cate2.setOrnum(2);
        cate2.setUptim(cate.getCrtim());
        cateRepo.save(cate2);

        BpmBusTmplEntity tmpl = new BpmBusTmplEntity();
        tmpl.setId(101L);
        tmpl.setName("请假申请");
        tmpl.setAvtag(true);
        tmpl.setUptim(tmpl.getCrtim());
        tmpl.setCatid(1L);
        tmpl.setFpath("bpm/bus/leave/edit.vue");
        tmpl.setFtype(1);
        tmpl.setPrdid(101L);
        tmpl.setCruid("1");
        tmplRepo.save(tmpl);

        BpmProcDefEntity def=new BpmProcDefEntity();
        def.setId(101L);
        def.setName("请假申请");
        def.setUptim(def.getCrtim());
        def.setBusid(101L);
        def.setDixml("""
            <?xml version="1.0" encoding="UTF-8"?>
            <bpmn2:definitions xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" xmlns:bpmn2="http://www.omg.org/spec/BPMN/20100524/MODEL" xmlns:bpmndi="http://www.omg.org/spec/BPMN/20100524/DI" xmlns:dc="http://www.omg.org/spec/DD/20100524/DC" xmlns:di="http://www.omg.org/spec/DD/20100524/DI" xmlns:activiti="http://activiti.org/bpmn" id="sample-diagram" targetNamespace="http://activiti.org/bpmn" xsi:schemaLocation="http://www.omg.org/spec/BPMN/20100524/MODEL BPMN20.xsd">
              <bpmn2:process id="Process_1" name="1" isExecutable="true">
                <bpmn2:startEvent id="NS" name="开始节点">
                  <bpmn2:outgoing>L1</bpmn2:outgoing>
                </bpmn2:startEvent>
                <bpmn2:sequenceFlow id="L1" sourceRef="NS" targetRef="N1" />
                <bpmn2:endEvent id="NE" name="结束节点">
                  <bpmn2:incoming>Flow_1y4gj3o</bpmn2:incoming>
                </bpmn2:endEvent>
                <bpmn2:userTask id="N1" name="起草节点" activiti:assignee="l4" activiti:candidateUsers="">
                  <bpmn2:documentation>起草节点，表单数据一般从绑定的表单提取</bpmn2:documentation>
                  <bpmn2:extensionElements>
                    <activiti:formProperty id="userid" type="string" />
                    <activiti:formProperty id="money" type="int" />
                    <activiti:properties>
                      <activiti:property name="编辑" value="edit" />
                      <activiti:property name="撤回" value="back" />
                      <activiti:property name="提交" value="commit" />
                    </activiti:properties>
                  </bpmn2:extensionElements>
                  <bpmn2:incoming>L1</bpmn2:incoming>
                  <bpmn2:outgoing>L2</bpmn2:outgoing>
                </bpmn2:userTask>
                <bpmn2:sequenceFlow id="L2" sourceRef="N1" targetRef="N2" />
                <bpmn2:userTask id="N2" name="张三" custom="2" hamen="u3" flway="1" hatyp="1">
                  <bpmn2:incoming>L2</bpmn2:incoming>
                  <bpmn2:outgoing>Flow_1bq3j11</bpmn2:outgoing>
                </bpmn2:userTask>
                <bpmn2:sequenceFlow id="Flow_1bq3j11" sourceRef="N2" targetRef="N3" />
                <bpmn2:userTask id="N3" name="李四" custom="2" hamen="u4" flway="1" hatyp="1">
                  <bpmn2:incoming>Flow_1bq3j11</bpmn2:incoming>
                  <bpmn2:outgoing>Flow_18t60da</bpmn2:outgoing>
                </bpmn2:userTask>
                <bpmn2:sequenceFlow id="Flow_18t60da" sourceRef="N3" targetRef="N4" />
                <bpmn2:userTask id="N4" name="王五" custom="2" hamen="u5" flway="1" hatyp="1">
                  <bpmn2:incoming>Flow_18t60da</bpmn2:incoming>
                  <bpmn2:outgoing>Flow_1y4gj3o</bpmn2:outgoing>
                </bpmn2:userTask>
                <bpmn2:sequenceFlow id="Flow_1y4gj3o" sourceRef="N4" targetRef="NE" />
              </bpmn2:process>
              <bpmndi:BPMNDiagram id="BPMNDiagram_1">
                <bpmndi:BPMNPlane id="BPMNPlane_1" bpmnElement="Process_1">
                  <bpmndi:BPMNEdge id="Flow_0rj5mf6_di" bpmnElement="L2">
                    <di:waypoint x="360" y="240" />
                    <di:waypoint x="360" y="300" />
                  </bpmndi:BPMNEdge>
                  <bpmndi:BPMNEdge id="Flow_1u6pmzo_di" bpmnElement="L1">
                    <di:waypoint x="360" y="78" />
                    <di:waypoint x="360" y="160" />
                  </bpmndi:BPMNEdge>
                  <bpmndi:BPMNEdge id="Flow_1bq3j11_di" bpmnElement="Flow_1bq3j11">
                    <di:waypoint x="360" y="380" />
                    <di:waypoint x="360" y="440" />
                  </bpmndi:BPMNEdge>
                  <bpmndi:BPMNEdge id="Flow_18t60da_di" bpmnElement="Flow_18t60da">
                    <di:waypoint x="360" y="520" />
                    <di:waypoint x="360" y="560" />
                  </bpmndi:BPMNEdge>
                  <bpmndi:BPMNEdge id="Flow_1y4gj3o_di" bpmnElement="Flow_1y4gj3o">
                    <di:waypoint x="360" y="640" />
                    <di:waypoint x="360" y="722" />
                  </bpmndi:BPMNEdge>
                  <bpmndi:BPMNShape id="Event_0byql27_di" bpmnElement="NS">
                    <dc:Bounds x="342" y="42" width="36" height="36" />
                    <bpmndi:BPMNLabel>
                      <dc:Bounds x="339" y="12" width="43" height="14" />
                    </bpmndi:BPMNLabel>
                  </bpmndi:BPMNShape>
                  <bpmndi:BPMNShape id="Activity_0g48n8q_di" bpmnElement="N1">
                    <dc:Bounds x="310" y="160" width="100" height="80" />
                  </bpmndi:BPMNShape>
                  <bpmndi:BPMNShape id="N2_di" bpmnElement="N2">
                    <dc:Bounds x="310" y="300" width="100" height="80" />
                  </bpmndi:BPMNShape>
                  <bpmndi:BPMNShape id="N3_di" bpmnElement="N3">
                    <dc:Bounds x="310" y="440" width="100" height="80" />
                  </bpmndi:BPMNShape>
                  <bpmndi:BPMNShape id="Event_1h4oob7_di" bpmnElement="NE">
                    <dc:Bounds x="342" y="722" width="36" height="36" />
                    <bpmndi:BPMNLabel>
                      <dc:Bounds x="339" y="765" width="44" height="14" />
                    </bpmndi:BPMNLabel>
                  </bpmndi:BPMNShape>
                  <bpmndi:BPMNShape id="N4_di" bpmnElement="N4">
                    <dc:Bounds x="310" y="560" width="100" height="80" />
                  </bpmndi:BPMNShape>
                </bpmndi:BPMNPlane>
              </bpmndi:BPMNDiagram>
            </bpmn2:definitions>
            """);
        def.setExxml("""
            <?xml version="1.0" encoding="gb2312"?>
            <process id="Process_1" name="1" isExecutable="true">
                <startEvent id="NS" name="开始节点">
                  <outgoing>L1</outgoing>
                </startEvent>
                <sequenceFlow id="L1" sourceRef="NS" targetRef="N1" />
                <endEvent id="NE" name="结束节点">
                  <incoming>Flow_1y4gj3o</incoming>
                </endEvent>
                <userTask id="N1" name="起草节点" assignee="l4" candidateUsers="">
                  <documentation>起草节点，表单数据一般从绑定的表单提取</documentation>
                  <extensionElements>
                    <formProperty id="userid" type="string" />
                    <formProperty id="money" type="int" />
                    <properties>
                      <property name="编辑" value="edit" />
                      <property name="撤回" value="back" />
                      <property name="提交" value="commit" />
                    </properties>
                  </extensionElements>
                  <incoming>L1</incoming>
                  <outgoing>L2</outgoing>
                </userTask>
                <sequenceFlow id="L2" sourceRef="N1" targetRef="N2" />
                <userTask id="N2" name="张三" custom="2" hamen="u3" flway="1" hatyp="1">
                  <incoming>L2</incoming>
                  <outgoing>Flow_1bq3j11</outgoing>
                </userTask>
                <sequenceFlow id="Flow_1bq3j11" sourceRef="N2" targetRef="N3" />
                <userTask id="N3" name="李四" custom="2" hamen="u4" flway="1" hatyp="1">
                  <incoming>Flow_1bq3j11</incoming>
                  <outgoing>Flow_18t60da</outgoing>
                </userTask>
                <sequenceFlow id="Flow_18t60da" sourceRef="N3" targetRef="N4" />
                <userTask id="N4" name="王五" custom="2" hamen="u5" flway="1" hatyp="1">
                  <incoming>Flow_18t60da</incoming>
                  <outgoing>Flow_1y4gj3o</outgoing>
                </userTask>
                <sequenceFlow id="Flow_1y4gj3o" sourceRef="N4" targetRef="NE" />
              </process>
            """);
        defRepo.save(def);

        BpmBusTmplEntity tmpl2 = new BpmBusTmplEntity();
        tmpl2.setId(102L);
        tmpl2.setName("费用报销");
        tmpl2.setAvtag(true);
        tmpl2.setUptim(tmpl.getCrtim());
        tmpl2.setCatid(2L);
        tmpl2.setFtype(2);
        tmpl2.setPrdid(102L);
        tmpl2.setCruid("1");
        tmpl2.setFrule("""
            [{"type":"input","field":"Frtvmgptja5aabc","title":"输入框","info":"","$required":false,"_fc_id":"id_F7hhmgptja5aacc","name":"ref_Fjk7mgptja5aadc","display":true,"hidden":false,"_fc_drag_tag":"input"},{"type":"inputNumber","field":"Fgo2mgptjbi6aec","title":"计数器","info":"","$required":false,"_fc_id":"id_Fa9kmgptjbi6afc","name":"ref_Flp4mgptjbi6agc","display":true,"hidden":false,"_fc_drag_tag":"inputNumber"},{"type":"rate","field":"F5tgmgptjck1ahc","title":"评分","info":"","$required":false,"_fc_id":"id_F9xrmgptjck1aic","name":"ref_Faimmgptjck1ajc","display":true,"hidden":false,"_fc_drag_tag":"rate","value":0},{"type":"colorPicker","field":"Foh5mgptjf6sakc","title":"颜色选择器","info":"","$required":false,"_fc_id":"id_Fyctmgptjf6salc","name":"ref_Fz0wmgptjf6samc","display":true,"hidden":false,"_fc_drag_tag":"colorPicker"}]
            """);
        tmplRepo.save(tmpl2);

        BpmProcDefEntity def2=new BpmProcDefEntity();
        def2.setId(102L);
        def2.setName("费用报销");
        def2.setUptim(def.getCrtim());
        def2.setBusid(102L);
        def2.setDixml("""
            <?xml version="1.0" encoding="UTF-8"?>
            <bpmn2:definitions xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" xmlns:bpmn2="http://www.omg.org/spec/BPMN/20100524/MODEL" xmlns:bpmndi="http://www.omg.org/spec/BPMN/20100524/DI" xmlns:dc="http://www.omg.org/spec/DD/20100524/DC" xmlns:di="http://www.omg.org/spec/DD/20100524/DI" xmlns:activiti="http://activiti.org/bpmn" id="sample-diagram" targetNamespace="http://activiti.org/bpmn" xsi:schemaLocation="http://www.omg.org/spec/BPMN/20100524/MODEL BPMN20.xsd">
              <bpmn2:process id="Process_1" name="1" isExecutable="true">
                <bpmn2:startEvent id="NS" name="开始节点">
                  <bpmn2:outgoing>L1</bpmn2:outgoing>
                </bpmn2:startEvent>
                <bpmn2:sequenceFlow id="L1" sourceRef="NS" targetRef="N1" />
                <bpmn2:endEvent id="NE" name="结束节点">
                  <bpmn2:incoming>Flow_1w4wv04</bpmn2:incoming>
                </bpmn2:endEvent>
                <bpmn2:userTask id="N1" name="起草节点" activiti:assignee="l4" activiti:candidateUsers="">
                  <bpmn2:documentation>起草节点，表单数据一般从绑定的表单提取</bpmn2:documentation>
                  <bpmn2:extensionElements>
                    <activiti:formProperty id="userid" type="string" />
                    <activiti:formProperty id="money" type="int" />
                    <activiti:properties>
                      <activiti:property name="编辑" value="edit" />
                      <activiti:property name="撤回" value="back" />
                      <activiti:property name="提交" value="commit" />
                    </activiti:properties>
                  </bpmn2:extensionElements>
                  <bpmn2:incoming>L1</bpmn2:incoming>
                  <bpmn2:outgoing>L2</bpmn2:outgoing>
                </bpmn2:userTask>
                <bpmn2:sequenceFlow id="L2" sourceRef="N1" targetRef="N2" />
                <bpmn2:userTask id="N2" name="张三" custom="2" hamen="u3" flway="1" hatyp="1">
                  <bpmn2:incoming>L2</bpmn2:incoming>
                  <bpmn2:outgoing>Flow_0opcpii</bpmn2:outgoing>
                </bpmn2:userTask>
                <bpmn2:sequenceFlow id="Flow_0opcpii" sourceRef="N2" targetRef="N3" />
                <bpmn2:userTask id="N3" name="李四" custom="2" hamen="u4" flway="1" hatyp="1">
                  <bpmn2:incoming>Flow_0opcpii</bpmn2:incoming>
                  <bpmn2:outgoing>Flow_15dokow</bpmn2:outgoing>
                </bpmn2:userTask>
                <bpmn2:sequenceFlow id="Flow_15dokow" sourceRef="N3" targetRef="N4" />
                <bpmn2:userTask id="N4" name="王五" custom="2" hamen="u5" flway="1" hatyp="1">
                  <bpmn2:incoming>Flow_15dokow</bpmn2:incoming>
                  <bpmn2:outgoing>Flow_1w4wv04</bpmn2:outgoing>
                </bpmn2:userTask>
                <bpmn2:sequenceFlow id="Flow_1w4wv04" sourceRef="N4" targetRef="NE" />
              </bpmn2:process>
              <bpmndi:BPMNDiagram id="BPMNDiagram_1">
                <bpmndi:BPMNPlane id="BPMNPlane_1" bpmnElement="Process_1">
                  <bpmndi:BPMNEdge id="Flow_1w4wv04_di" bpmnElement="Flow_1w4wv04">
                    <di:waypoint x="870" y="180" />
                    <di:waypoint x="922" y="180" />
                  </bpmndi:BPMNEdge>
                  <bpmndi:BPMNEdge id="Flow_15dokow_di" bpmnElement="Flow_15dokow">
                    <di:waypoint x="710" y="180" />
                    <di:waypoint x="770" y="180" />
                  </bpmndi:BPMNEdge>
                  <bpmndi:BPMNEdge id="Flow_0opcpii_di" bpmnElement="Flow_0opcpii">
                    <di:waypoint x="560" y="180" />
                    <di:waypoint x="610" y="180" />
                  </bpmndi:BPMNEdge>
                  <bpmndi:BPMNEdge id="Flow_0rj5mf6_di" bpmnElement="L2">
                    <di:waypoint x="400" y="180" />
                    <di:waypoint x="460" y="180" />
                  </bpmndi:BPMNEdge>
                  <bpmndi:BPMNEdge id="Flow_1u6pmzo_di" bpmnElement="L1">
                    <di:waypoint x="228" y="180" />
                    <di:waypoint x="300" y="180" />
                  </bpmndi:BPMNEdge>
                  <bpmndi:BPMNShape id="Event_0byql27_di" bpmnElement="NS">
                    <dc:Bounds x="192" y="162" width="36" height="36" />
                    <bpmndi:BPMNLabel>
                      <dc:Bounds x="189" y="132" width="44" height="14" />
                    </bpmndi:BPMNLabel>
                  </bpmndi:BPMNShape>
                  <bpmndi:BPMNShape id="Event_1h4oob7_di" bpmnElement="NE">
                    <dc:Bounds x="922" y="162" width="36" height="36" />
                    <bpmndi:BPMNLabel>
                      <dc:Bounds x="919" y="205" width="44" height="14" />
                    </bpmndi:BPMNLabel>
                  </bpmndi:BPMNShape>
                  <bpmndi:BPMNShape id="Activity_0g48n8q_di" bpmnElement="N1">
                    <dc:Bounds x="300" y="140" width="100" height="80" />
                  </bpmndi:BPMNShape>
                  <bpmndi:BPMNShape id="N2_di" bpmnElement="N2">
                    <dc:Bounds x="460" y="140" width="100" height="80" />
                  </bpmndi:BPMNShape>
                  <bpmndi:BPMNShape id="N3_di" bpmnElement="N3">
                    <dc:Bounds x="610" y="140" width="100" height="80" />
                  </bpmndi:BPMNShape>
                  <bpmndi:BPMNShape id="N4_di" bpmnElement="N4">
                    <dc:Bounds x="770" y="140" width="100" height="80" />
                  </bpmndi:BPMNShape>
                </bpmndi:BPMNPlane>
              </bpmndi:BPMNDiagram>
            </bpmn2:definitions>
            """);
        def2.setExxml("""
            <?xml version="1.0" encoding="gb2312"?>
            <process id="Process_1" name="1" isExecutable="true">
                <startEvent id="NS" name="开始节点">
                  <outgoing>L1</outgoing>
                </startEvent>
                <sequenceFlow id="L1" sourceRef="NS" targetRef="N1" />
                <endEvent id="NE" name="结束节点">
                  <incoming>Flow_1w4wv04</incoming>
                </endEvent>
                <userTask id="N1" name="起草节点" assignee="l4" candidateUsers="">
                  <documentation>起草节点，表单数据一般从绑定的表单提取</documentation>
                  <extensionElements>
                    <formProperty id="userid" type="string" />
                    <formProperty id="money" type="int" />
                    <properties>
                      <property name="编辑" value="edit" />
                      <property name="撤回" value="back" />
                      <property name="提交" value="commit" />
                    </properties>
                  </extensionElements>
                  <incoming>L1</incoming>
                  <outgoing>L2</outgoing>
                </userTask>
                <sequenceFlow id="L2" sourceRef="N1" targetRef="N2" />
                <userTask id="N2" name="张三" custom="2" hamen="u3" flway="1" hatyp="1">
                  <incoming>L2</incoming>
                  <outgoing>Flow_0opcpii</outgoing>
                </userTask>
                <sequenceFlow id="Flow_0opcpii" sourceRef="N2" targetRef="N3" />
                <userTask id="N3" name="李四" custom="2" hamen="u4" flway="1" hatyp="1">
                  <incoming>Flow_0opcpii</incoming>
                  <outgoing>Flow_15dokow</outgoing>
                </userTask>
                <sequenceFlow id="Flow_15dokow" sourceRef="N3" targetRef="N4" />
                <userTask id="N4" name="王五" custom="2" hamen="u5" flway="1" hatyp="1">
                  <incoming>Flow_15dokow</incoming>
                  <outgoing>Flow_1w4wv04</outgoing>
                </userTask>
                <sequenceFlow id="Flow_1w4wv04" sourceRef="N4" targetRef="NE" />
              </process>
            """);
        defRepo.save(def2);

    }

    private final BpmBusCateRepo cateRepo;

    private final BpmBusTmplRepo tmplRepo;

    private final BpmProcDefRepo defRepo;

}

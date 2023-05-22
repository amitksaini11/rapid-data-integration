package com.aks.framework.rdi.dbexecutor;

import static com.aks.framework.rdi.base.DataFlowUtils.createChannel;

import com.aks.framework.rdi.base.DataFlowBaseExecutor;
import com.aks.framework.rdi.base.DataFlowConstants;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.integration.dsl.IntegrationFlowDefinition;

public class DefaultDBExecutorFlow extends AbstractDBExecutorFlow {

  private Class<?> entityClass;
  private Class<? extends JpaRepository> repositoryClass;

  public DefaultDBExecutorFlow(
      DataFlowBaseExecutor dbExecutor,
      String dataFlowName,
      Class<?> entityClass,
      Class<? extends JpaRepository> repositoryClass) {
    super(dbExecutor, dataFlowName);
    this.entityClass = entityClass;
    this.repositoryClass = repositoryClass;
  }

  @Override
  public IntegrationFlowDefinition<?> buildFlow() {
    return new DBExecutorDefinition(
            dataFlowBaseExecutor, dataFlowName, entityClass, repositoryClass)
        .from(createChannel(dataFlowName, DataFlowConstants.DB_EXECUTOR_CHANNEL))
        .transformRequest()
        .executeRequest()
        .transformResponse()
        .bridge();
  }
}

// Generated by the protocol buffer compiler.  DO NOT EDIT!
// source: service.proto

public final class HelloWorldProto {
  private HelloWorldProto() {}
  public static void registerAllExtensions(
      com.google.protobuf.ExtensionRegistryLite registry) {
  }

  public static void registerAllExtensions(
      com.google.protobuf.ExtensionRegistry registry) {
    registerAllExtensions(
        (com.google.protobuf.ExtensionRegistryLite) registry);
  }
  static final com.google.protobuf.Descriptors.Descriptor
    internal_static_ListarAlunosRequest_descriptor;
  static final 
    com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
      internal_static_ListarAlunosRequest_fieldAccessorTable;
  static final com.google.protobuf.Descriptors.Descriptor
    internal_static_ListarAlunosResponse_descriptor;
  static final 
    com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
      internal_static_ListarAlunosResponse_fieldAccessorTable;
  static final com.google.protobuf.Descriptors.Descriptor
    internal_static_ListarAlunosResponse_Aluno_descriptor;
  static final 
    com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
      internal_static_ListarAlunosResponse_Aluno_fieldAccessorTable;
  static final com.google.protobuf.Descriptors.Descriptor
    internal_static_AlterarNotaRequest_descriptor;
  static final 
    com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
      internal_static_AlterarNotaRequest_fieldAccessorTable;
  static final com.google.protobuf.Descriptors.Descriptor
    internal_static_AlterarNotaResponse_descriptor;
  static final 
    com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
      internal_static_AlterarNotaResponse_fieldAccessorTable;
  static final com.google.protobuf.Descriptors.Descriptor
    internal_static_AlterarFaltasRequest_descriptor;
  static final 
    com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
      internal_static_AlterarFaltasRequest_fieldAccessorTable;
  static final com.google.protobuf.Descriptors.Descriptor
    internal_static_AlterarFaltasResponse_descriptor;
  static final 
    com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
      internal_static_AlterarFaltasResponse_fieldAccessorTable;
  static final com.google.protobuf.Descriptors.Descriptor
    internal_static_ListarDisciplinasAlunoRequest_descriptor;
  static final 
    com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
      internal_static_ListarDisciplinasAlunoRequest_fieldAccessorTable;
  static final com.google.protobuf.Descriptors.Descriptor
    internal_static_ListarDisciplinasAlunoResponse_descriptor;
  static final 
    com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
      internal_static_ListarDisciplinasAlunoResponse_fieldAccessorTable;
  static final com.google.protobuf.Descriptors.Descriptor
    internal_static_ListarDisciplinasAlunoResponse_DisciplinaAlunos_descriptor;
  static final 
    com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
      internal_static_ListarDisciplinasAlunoResponse_DisciplinaAlunos_fieldAccessorTable;
  static final com.google.protobuf.Descriptors.Descriptor
    internal_static_InserirMatriculaRequest_descriptor;
  static final 
    com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
      internal_static_InserirMatriculaRequest_fieldAccessorTable;
  static final com.google.protobuf.Descriptors.Descriptor
    internal_static_Matricula_descriptor;
  static final 
    com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
      internal_static_Matricula_fieldAccessorTable;
  static final com.google.protobuf.Descriptors.Descriptor
    internal_static_InserirMatriculaResponse_descriptor;
  static final 
    com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
      internal_static_InserirMatriculaResponse_fieldAccessorTable;
  static final com.google.protobuf.Descriptors.Descriptor
    internal_static_ListarDisciplinasCursoRequest_descriptor;
  static final 
    com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
      internal_static_ListarDisciplinasCursoRequest_fieldAccessorTable;
  static final com.google.protobuf.Descriptors.Descriptor
    internal_static_ListarDisciplinasCursoResponse_descriptor;
  static final 
    com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
      internal_static_ListarDisciplinasCursoResponse_fieldAccessorTable;
  static final com.google.protobuf.Descriptors.Descriptor
    internal_static_ListarDisciplinasCursoResponse_DisciplinaCurso_descriptor;
  static final 
    com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
      internal_static_ListarDisciplinasCursoResponse_DisciplinaCurso_fieldAccessorTable;

  public static com.google.protobuf.Descriptors.FileDescriptor
      getDescriptor() {
    return descriptor;
  }
  private static  com.google.protobuf.Descriptors.FileDescriptor
      descriptor;
  static {
    java.lang.String[] descriptorData = {
      "\n\rservice.proto\"N\n\023ListarAlunosRequest\022\030" +
      "\n\020codigoDisciplina\030\001 \001(\t\022\013\n\003ano\030\002 \001(\005\022\020\n" +
      "\010semestre\030\003 \001(\005\"\211\001\n\024ListarAlunosResponse" +
      "\022+\n\006alunos\030\001 \003(\0132\033.ListarAlunosResponse." +
      "Aluno\022\020\n\010mensagem\030\002 \001(\t\0322\n\005Aluno\022\n\n\002ra\030\001" +
      " \001(\005\022\014\n\004nome\030\002 \001(\t\022\017\n\007periodo\030\004 \001(\005\"g\n\022A" +
      "lterarNotaRequest\022\n\n\002ra\030\001 \001(\005\022\030\n\020codigoD" +
      "isciplina\030\002 \001(\t\022\013\n\003ano\030\003 \001(\005\022\020\n\010semestre" +
      "\030\004 \001(\005\022\014\n\004nota\030\005 \001(\002\"z\n\023AlterarNotaRespo" +
      "nse\022\n\n\002ra\030\001 \001(\005\022\030\n\020codigoDisciplina\030\002 \001(" +
      "\t\022\013\n\003ano\030\003 \001(\005\022\020\n\010semestre\030\004 \001(\005\022\014\n\004nota" +
      "\030\005 \001(\002\022\020\n\010mensagem\030\007 \001(\t\"k\n\024AlterarFalta" +
      "sRequest\022\n\n\002ra\030\001 \001(\005\022\030\n\020codigoDisciplina" +
      "\030\002 \001(\t\022\013\n\003ano\030\003 \001(\005\022\020\n\010semestre\030\004 \001(\005\022\016\n" +
      "\006faltas\030\005 \001(\005\"~\n\025AlterarFaltasResponse\022\n" +
      "\n\002ra\030\001 \001(\005\022\030\n\020codigoDisciplina\030\002 \001(\t\022\013\n\003" +
      "ano\030\003 \001(\005\022\020\n\010semestre\030\004 \001(\005\022\016\n\006faltas\030\005 " +
      "\001(\005\022\020\n\010mensagem\030\006 \001(\t\"J\n\035ListarDisciplin" +
      "asAlunoRequest\022\n\n\002ra\030\001 \001(\005\022\013\n\003ano\030\002 \001(\005\022" +
      "\020\n\010semestre\030\003 \001(\005\"\321\001\n\036ListarDisciplinasA" +
      "lunoResponse\022E\n\013disciplinas\030\001 \003(\01320.List" +
      "arDisciplinasAlunoResponse.DisciplinaAlu" +
      "nos\022\020\n\010mensagem\030\002 \001(\t\032V\n\020DisciplinaAluno" +
      "s\022\n\n\002ra\030\001 \001(\005\022\030\n\020codigoDisciplina\030\002 \001(\t\022" +
      "\014\n\004nota\030\003 \001(\002\022\016\n\006faltas\030\004 \001(\005\"8\n\027Inserir" +
      "MatriculaRequest\022\035\n\tmatricula\030\001 \001(\0132\n.Ma" +
      "tricula\"n\n\tMatricula\022\n\n\002ra\030\001 \001(\005\022\030\n\020codi" +
      "goDisciplina\030\002 \001(\t\022\013\n\003ano\030\003 \001(\005\022\020\n\010semes" +
      "tre\030\004 \001(\005\022\016\n\006faltas\030\005 \001(\005\022\014\n\004nota\030\006 \001(\002\"" +
      "K\n\030InserirMatriculaResponse\022\035\n\tmatricula" +
      "\030\001 \001(\0132\n.Matricula\022\020\n\010mensagem\030\002 \001(\t\"4\n\035" +
      "ListarDisciplinasCursoRequest\022\023\n\013codigoC" +
      "urso\030\001 \001(\005\"\306\001\n\036ListarDisciplinasCursoRes" +
      "ponse\022D\n\013disciplinas\030\001 \003(\0132/.ListarDisci" +
      "plinasCursoResponse.DisciplinaCurso\022\020\n\010m" +
      "ensagem\030\002 \001(\t\032L\n\017DisciplinaCurso\022\030\n\020codi" +
      "goDisciplina\030\001 \001(\t\022\014\n\004nome\030\002 \001(\t\022\021\n\tprof" +
      "essor\030\003 \001(\t2\326\003\n\022GerenciadorDeNotas\022=\n\014Li" +
      "starAlunos\022\024.ListarAlunosRequest\032\025.Lista" +
      "rAlunosResponse\"\000\022:\n\013AlterarNota\022\023.Alter" +
      "arNotaRequest\032\024.AlterarNotaResponse\"\000\022@\n" +
      "\rAlterarFaltas\022\025.AlterarFaltasRequest\032\026." +
      "AlterarFaltasResponse\"\000\022[\n\026ListarDiscipl" +
      "inasCurso\022\036.ListarDisciplinasCursoReques" +
      "t\032\037.ListarDisciplinasCursoResponse\"\000\022[\n\026" +
      "ListarDisciplinasAluno\022\036.ListarDisciplin" +
      "asAlunoRequest\032\037.ListarDisciplinasAlunoR" +
      "esponse\"\000\022I\n\020InserirMatricula\022\030.InserirM" +
      "atriculaRequest\032\031.InserirMatriculaRespon" +
      "se\"\000B\023B\017HelloWorldProtoP\001b\006proto3"
    };
    descriptor = com.google.protobuf.Descriptors.FileDescriptor
      .internalBuildGeneratedFileFrom(descriptorData,
        new com.google.protobuf.Descriptors.FileDescriptor[] {
        });
    internal_static_ListarAlunosRequest_descriptor =
      getDescriptor().getMessageTypes().get(0);
    internal_static_ListarAlunosRequest_fieldAccessorTable = new
      com.google.protobuf.GeneratedMessageV3.FieldAccessorTable(
        internal_static_ListarAlunosRequest_descriptor,
        new java.lang.String[] { "CodigoDisciplina", "Ano", "Semestre", });
    internal_static_ListarAlunosResponse_descriptor =
      getDescriptor().getMessageTypes().get(1);
    internal_static_ListarAlunosResponse_fieldAccessorTable = new
      com.google.protobuf.GeneratedMessageV3.FieldAccessorTable(
        internal_static_ListarAlunosResponse_descriptor,
        new java.lang.String[] { "Alunos", "Mensagem", });
    internal_static_ListarAlunosResponse_Aluno_descriptor =
      internal_static_ListarAlunosResponse_descriptor.getNestedTypes().get(0);
    internal_static_ListarAlunosResponse_Aluno_fieldAccessorTable = new
      com.google.protobuf.GeneratedMessageV3.FieldAccessorTable(
        internal_static_ListarAlunosResponse_Aluno_descriptor,
        new java.lang.String[] { "Ra", "Nome", "Periodo", });
    internal_static_AlterarNotaRequest_descriptor =
      getDescriptor().getMessageTypes().get(2);
    internal_static_AlterarNotaRequest_fieldAccessorTable = new
      com.google.protobuf.GeneratedMessageV3.FieldAccessorTable(
        internal_static_AlterarNotaRequest_descriptor,
        new java.lang.String[] { "Ra", "CodigoDisciplina", "Ano", "Semestre", "Nota", });
    internal_static_AlterarNotaResponse_descriptor =
      getDescriptor().getMessageTypes().get(3);
    internal_static_AlterarNotaResponse_fieldAccessorTable = new
      com.google.protobuf.GeneratedMessageV3.FieldAccessorTable(
        internal_static_AlterarNotaResponse_descriptor,
        new java.lang.String[] { "Ra", "CodigoDisciplina", "Ano", "Semestre", "Nota", "Mensagem", });
    internal_static_AlterarFaltasRequest_descriptor =
      getDescriptor().getMessageTypes().get(4);
    internal_static_AlterarFaltasRequest_fieldAccessorTable = new
      com.google.protobuf.GeneratedMessageV3.FieldAccessorTable(
        internal_static_AlterarFaltasRequest_descriptor,
        new java.lang.String[] { "Ra", "CodigoDisciplina", "Ano", "Semestre", "Faltas", });
    internal_static_AlterarFaltasResponse_descriptor =
      getDescriptor().getMessageTypes().get(5);
    internal_static_AlterarFaltasResponse_fieldAccessorTable = new
      com.google.protobuf.GeneratedMessageV3.FieldAccessorTable(
        internal_static_AlterarFaltasResponse_descriptor,
        new java.lang.String[] { "Ra", "CodigoDisciplina", "Ano", "Semestre", "Faltas", "Mensagem", });
    internal_static_ListarDisciplinasAlunoRequest_descriptor =
      getDescriptor().getMessageTypes().get(6);
    internal_static_ListarDisciplinasAlunoRequest_fieldAccessorTable = new
      com.google.protobuf.GeneratedMessageV3.FieldAccessorTable(
        internal_static_ListarDisciplinasAlunoRequest_descriptor,
        new java.lang.String[] { "Ra", "Ano", "Semestre", });
    internal_static_ListarDisciplinasAlunoResponse_descriptor =
      getDescriptor().getMessageTypes().get(7);
    internal_static_ListarDisciplinasAlunoResponse_fieldAccessorTable = new
      com.google.protobuf.GeneratedMessageV3.FieldAccessorTable(
        internal_static_ListarDisciplinasAlunoResponse_descriptor,
        new java.lang.String[] { "Disciplinas", "Mensagem", });
    internal_static_ListarDisciplinasAlunoResponse_DisciplinaAlunos_descriptor =
      internal_static_ListarDisciplinasAlunoResponse_descriptor.getNestedTypes().get(0);
    internal_static_ListarDisciplinasAlunoResponse_DisciplinaAlunos_fieldAccessorTable = new
      com.google.protobuf.GeneratedMessageV3.FieldAccessorTable(
        internal_static_ListarDisciplinasAlunoResponse_DisciplinaAlunos_descriptor,
        new java.lang.String[] { "Ra", "CodigoDisciplina", "Nota", "Faltas", });
    internal_static_InserirMatriculaRequest_descriptor =
      getDescriptor().getMessageTypes().get(8);
    internal_static_InserirMatriculaRequest_fieldAccessorTable = new
      com.google.protobuf.GeneratedMessageV3.FieldAccessorTable(
        internal_static_InserirMatriculaRequest_descriptor,
        new java.lang.String[] { "Matricula", });
    internal_static_Matricula_descriptor =
      getDescriptor().getMessageTypes().get(9);
    internal_static_Matricula_fieldAccessorTable = new
      com.google.protobuf.GeneratedMessageV3.FieldAccessorTable(
        internal_static_Matricula_descriptor,
        new java.lang.String[] { "Ra", "CodigoDisciplina", "Ano", "Semestre", "Faltas", "Nota", });
    internal_static_InserirMatriculaResponse_descriptor =
      getDescriptor().getMessageTypes().get(10);
    internal_static_InserirMatriculaResponse_fieldAccessorTable = new
      com.google.protobuf.GeneratedMessageV3.FieldAccessorTable(
        internal_static_InserirMatriculaResponse_descriptor,
        new java.lang.String[] { "Matricula", "Mensagem", });
    internal_static_ListarDisciplinasCursoRequest_descriptor =
      getDescriptor().getMessageTypes().get(11);
    internal_static_ListarDisciplinasCursoRequest_fieldAccessorTable = new
      com.google.protobuf.GeneratedMessageV3.FieldAccessorTable(
        internal_static_ListarDisciplinasCursoRequest_descriptor,
        new java.lang.String[] { "CodigoCurso", });
    internal_static_ListarDisciplinasCursoResponse_descriptor =
      getDescriptor().getMessageTypes().get(12);
    internal_static_ListarDisciplinasCursoResponse_fieldAccessorTable = new
      com.google.protobuf.GeneratedMessageV3.FieldAccessorTable(
        internal_static_ListarDisciplinasCursoResponse_descriptor,
        new java.lang.String[] { "Disciplinas", "Mensagem", });
    internal_static_ListarDisciplinasCursoResponse_DisciplinaCurso_descriptor =
      internal_static_ListarDisciplinasCursoResponse_descriptor.getNestedTypes().get(0);
    internal_static_ListarDisciplinasCursoResponse_DisciplinaCurso_fieldAccessorTable = new
      com.google.protobuf.GeneratedMessageV3.FieldAccessorTable(
        internal_static_ListarDisciplinasCursoResponse_DisciplinaCurso_descriptor,
        new java.lang.String[] { "CodigoDisciplina", "Nome", "Professor", });
  }

  // @@protoc_insertion_point(outer_class_scope)
}

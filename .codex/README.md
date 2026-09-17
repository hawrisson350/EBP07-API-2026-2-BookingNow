# Trabajo con IA en BookingNow

Esta carpeta conserva contexto, historias de usuario y evidencia del trabajo.
La estructura sigue el ejemplo de clase. Los archivos describen un flujo de
trabajo; no ejecutan agentes, pruebas ni despliegues automaticamente.

```text
.codex/
├── agent-memory/
│   └── contexto-proyecto.md
├── agents/
│   ├── analysis-userStory.md
│   ├── implementation-userStory.md
│   └── testing-userStory.md
├── instructions/
│   ├── 1.AnalysisUserStory.md
│   ├── 2.ImplementationUserStory.md
│   └── 3.TestingUserStory.md
├── stories/
│   ├── analysis/
│   ├── detail-user-stories/
│   ├── implementation/
│   ├── testing/
│   └── US_Template.md
```

## Donde colocar las HU

Las cinco historias del sprint tienen una ficha en
[detail-user-stories](stories/detail-user-stories/). Incluyen los requisitos recibidos en `workspace/Recuento HU.docx`.
La revision inicial identifica diferencias y dudas; no acredita implementacion.
Para otra historia, copiar [US_Template.md](stories/US_Template.md).

Conservar el identificador oficial `HU-XXXXXX` en todos sus documentos:

- `detail-user-stories/HU-XXXXXX-Titulo.md`: requisitos originales.
- `analysis/HU-XXXXXX-analysis.md`: analisis, dudas y propuesta tecnica.
- `implementation/HU-XXXXXX-implementation.md`: cambios realmente realizados.
- `testing/HU-XXXXXX-testing.md`: verificaciones y resultados reales.

Los archivos `TEMPLATE.md` de cada etapa son plantillas, no evidencia completada.

## Flujo

1. Completar la ficha con la HU y sus criterios de aceptacion.
2. Seguir [analisis](instructions/1.AnalysisUserStory.md).
3. Seguir [implementacion](instructions/2.ImplementationUserStory.md) dentro del alcance solicitado.
4. Seguir [pruebas](instructions/3.TestingUserStory.md) y registrar evidencia.
5. Actualizar la memoria solo con decisiones confirmadas y su fuente.

`agents/` define los roles y `instructions/` detalla sus pasos. Se pueden usar
secuencialmente en la misma conversacion; no requieren trabajo paralelo.
Codex lee las instrucciones del proyecto desde `AGENTS.md` en la raiz.
Este archivo remite a las guias y HU de `.codex/`. Los Markdown de `agents/`
son roles documentados del flujo, no definiciones de subagentes ejecutables.
No se necesita configuracion de permisos ni un archivo de ajustes local para este flujo.
Referencia: https://developers.openai.com/codex/guides/agents-md

Ejemplo de solicitud: "Analiza HU-010101 siguiendo
.codex/instructions/1.AnalysisUserStory.md".

No guardar contrasenas, JWT, cadenas de conexion con secretos ni datos personales
reales en fichas, memoria o reportes.

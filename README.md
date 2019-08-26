# Sleuth Custom MDC Attributes Removed

Change `spring.sleuth.sampler.probability` in `application.yml` to 0.0 to make the test pass.

Put break points in `HttpHandler.handleFinish` and `Slf4jScopeDecorator.decorateScope` to observe the behaviour.
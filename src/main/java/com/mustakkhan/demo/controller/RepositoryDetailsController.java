package com.mustakkhan.demo.controller;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.kohsuke.github.GHRepositorySearchBuilder;
import org.kohsuke.github.GitHub;
import org.kohsuke.github.GitHubBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import twitter4j.Trend;
import twitter4j.Trends;
import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;
import twitter4j.conf.ConfigurationBuilder;

@RestController
public class RepositoryDetailsController {




    @Autowired
    private Environment env;

	@RequestMapping(value = "/", produces = "text/html")
	public String getRepos() throws IOException {
		return "<!DOCTYPE html>"
			+ "<html lang='en'>"
			+ "<head>"
			+ "<meta charset='UTF-8'>"
			+ "<meta name='viewport' content='width=device-width, initial-scale=1.0'>"
			+ "<title>CloudOps Nexus | DevOps by Mustak Khan</title>"
			+ "<style>"
			+ "* { margin: 0; padding: 0; box-sizing: border-box; font-family: -apple-system, BlinkMacSystemFont, 'Segoe UI', Roboto, sans-serif; }"
			+ "body { background: #0a0f1d; color: #f1f5f9; min-height: 100vh; display: flex; flex-direction: column; align-items: center; padding: 40px 20px; }"
			+ ".container { max-width: 1100px; width: 100%; }"
			+ ".badge-live { display: inline-flex; align-items: center; gap: 8px; background: rgba(34, 197, 94, 0.15); color: #4ade80; border: 1px solid rgba(74, 222, 128, 0.3); padding: 6px 14px; border-radius: 9999px; font-size: 13px; font-weight: 600; text-transform: uppercase; letter-spacing: 0.5px; margin-bottom: 20px; }"
			+ ".pulse-dot { width: 8px; height: 8px; background: #22c55e; border-radius: 50%; box-shadow: 0 0 10px #22c55e; animation: pulse 2s infinite; }"
			+ "@keyframes pulse { 0%, 100% { opacity: 1; transform: scale(1); } 50% { opacity: 0.4; transform: scale(0.85); } }"
			+ ".hero { background: linear-gradient(135deg, rgba(30, 41, 59, 0.8), rgba(15, 23, 42, 0.9)); border: 1px solid rgba(148, 163, 184, 0.15); border-radius: 20px; padding: 40px; box-shadow: 0 20px 40px rgba(0, 0, 0, 0.4); backdrop-filter: blur(10px); margin-bottom: 30px; text-align: center; }"
			+ "h1 { font-size: 38px; font-weight: 800; background: linear-gradient(135deg, #38bdf8, #818cf8, #c084fc); -webkit-background-clip: text; -webkit-text-fill-color: transparent; margin-bottom: 12px; }"
			+ ".subtitle { font-size: 18px; color: #94a3b8; margin-bottom: 25px; }"
			+ ".author-pill { display: inline-block; background: #1e293b; color: #e2e8f0; padding: 8px 18px; border-radius: 12px; border: 1px solid #334155; font-size: 15px; font-weight: 500; margin-bottom: 20px; }"
			+ ".author-pill strong { color: #38bdf8; }"
			+ ".metrics-bar { display: grid; grid-template-columns: repeat(auto-fit, minmax(200px, 1fr)); gap: 15px; margin-top: 25px; }"
			+ ".metric-card { background: rgba(15, 23, 42, 0.6); border: 1px solid rgba(148, 163, 184, 0.1); border-radius: 12px; padding: 16px; text-align: left; }"
			+ ".metric-label { font-size: 12px; color: #64748b; text-transform: uppercase; font-weight: 700; letter-spacing: 0.5px; }"
			+ ".metric-value { font-size: 16px; color: #f8fafc; font-weight: 600; margin-top: 4px; }"
			+ ".section-title { font-size: 22px; font-weight: 700; color: #e2e8f0; margin: 30px 0 15px; text-align: left; display: flex; align-items: center; gap: 10px; }"
			+ ".grid { display: grid; grid-template-columns: repeat(auto-fit, minmax(240px, 1fr)); gap: 18px; }"
			+ ".card { background: #131b2e; border: 1px solid rgba(148, 163, 184, 0.1); border-radius: 14px; padding: 20px; transition: transform 0.2s ease, border-color 0.2s ease; text-align: left; }"
			+ ".card:hover { transform: translateY(-3px); border-color: #38bdf8; }"
			+ ".card-icon { font-size: 24px; margin-bottom: 10px; }"
			+ ".card h3 { font-size: 16px; font-weight: 600; color: #f1f5f9; margin-bottom: 6px; }"
			+ ".card p { font-size: 13px; color: #94a3b8; line-height: 1.5; }"
			+ ".actions { display: flex; justify-content: center; gap: 15px; flex-wrap: wrap; margin-top: 35px; }"
			+ ".btn { display: inline-flex; align-items: center; gap: 8px; padding: 12px 24px; border-radius: 10px; font-size: 14px; font-weight: 600; text-decoration: none; transition: all 0.2s ease; }"
			+ ".btn-primary { background: linear-gradient(135deg, #0284c7, #2563eb); color: white; box-shadow: 0 4px 15px rgba(2, 132, 199, 0.3); }"
			+ ".btn-primary:hover { opacity: 0.95; transform: scale(1.02); }"
			+ ".btn-secondary { background: #1e293b; color: #e2e8f0; border: 1px solid #334155; }"
			+ ".btn-secondary:hover { background: #334155; }"
			+ "footer { margin-top: 50px; text-align: center; color: #64748b; font-size: 13px; border-top: 1px solid rgba(148, 163, 184, 0.1); padding-top: 25px; }"
			+ "</style>"
			+ "</head>"
			+ "<body>"
			+ "<div class='container'>"
			+ "<div class='hero'>"
			+ "<div class='badge-live'><div class='pulse-dot'></div> Production Release 2.1.2 Live</div>"
			+ "<h1>CloudOps Nexus CI/CD Pipeline</h1>"
			+ "<p class='subtitle'>Mustak-to-End Enterprise Automation with Major DevOps Tools</p>"
			+ "<div class='author-pill'>Architected & Deployed by: <strong>Mustak Khan</strong></div>"
			+ "<div class='metrics-bar'>"
			+ "<div class='metric-card'><div class='metric-label'>Orchestrator</div><div class='metric-value'>AWS EKS v1.34</div></div>"
			+ "<div class='metric-card'><div class='metric-label'>Deployment Tool</div><div class='metric-value'>Helm 3 Chart</div></div>"
			+ "<div class='metric-card'><div class='metric-label'>CI Pipeline</div><div class='metric-value'>Jenkins (Master-Agent)</div></div>"
			+ "<div class='metric-card'><div class='metric-label'>Monitoring</div><div class='metric-value'>Prometheus &amp; Grafana</div></div>"
			+ "</div>"
			+ "</div>"
			+ "<div class='section-title'>⚙️ Pipeline Architecture &amp; Tech Stack</div>"
			+ "<div class='grid'>"
			+ "<div class='card'><div class='card-icon'>🏗️</div><h3>Terraform</h3><p>VPC, Subnets, Security Groups, EC2 nodes, and S3/DynamoDB state management.</p></div>"
			+ "<div class='card'><div class='card-icon'>⚡</div><h3>Ansible</h3><p>Automated node provisioning, package installation, and passwordless SSH.</p></div>"
			+ "<div class='card'><div class='card-icon'>👨‍💻</div><h3>Jenkins Master-Agent</h3><p>Distributed build agent executing Maven, SonarScanner, Docker &amp; Helm stages.</p></div>"
			+ "<div class='card'><div class='card-icon'>🔍</div><h3>SonarCloud</h3><p>Automated static analysis, code quality gates, and JaCoCo coverage reports.</p></div>"
			+ "<div class='card'><div class='card-icon'>📦</div><h3>JFrog Artifactory</h3><p>Enterprise artifact repository hosting versioned Java JAR releases.</p></div>"
			+ "<div class='card'><div class='card-icon'>🐳</div><h3>Docker &amp; AWS ECR</h3><p>Multi-stage container builds using Eclipse Temurin JRE runtime.</p></div>"
			+ "<div class='card'><div class='card-icon'>☸️</div><h3>Amazon EKS</h3><p>Production Kubernetes managed cluster with auto-scaling worker nodes.</p></div>"
			+ "<div class='card'><div class='card-icon'>📊</div><h3>Prometheus &amp; Grafana</h3><p>Cluster-wide real-time metric collection, health tracking, and dashboards.</p></div>"
			+ "</div>"
			+ "<div class='actions'>"
			+ "<a href='https://github.com/mustakkhan-dev' target='_blank' class='btn btn-primary'>🐙 GitHub: mustakkhan-dev</a>"
			+ "<a href='https://www.linkedin.com/in/mustakkhan66/' target='_blank' class='btn btn-primary' style='background: linear-gradient(135deg, #0077b5, #00a0dc);'>💼 LinkedIn: Mustak Khan</a>"
			+ "<a href='http://a61cbad1de3814a7b819dd6127a133cf-1655324334.us-east-1.elb.amazonaws.com' target='_blank' class='btn btn-secondary'>📊 View Grafana Dashboard</a>"
			+ "<a href='mailto:mkhanmustak06@gmail.com' class='btn btn-secondary'>✉️ Contact Mustak Khan</a>"
			+ "</div>"
			+ "<footer>"
			+ "<p>&copy; 2026 Advanced CI/CD DevOps Project &bull; Created with pride by <strong>Mustak Khan</strong></p>"
			+ "</footer>"
			+ "</div>"
			+ "</body>"
			+ "</html>";
	}

	@GetMapping("/trends")
	public Map<String, String> getTwitterTrends(@RequestParam("placeid") String trendPlace, @RequestParam("count") String trendCount) {
		String consumerKey = env.getProperty("CONSUMER_KEY");
		String consumerSecret = env.getProperty("CONSUMER_SECRET");
		String accessToken = env.getProperty("ACCESS_TOKEN");
		String accessTokenSecret = env.getProperty("ACCESS_TOKEN_SECRET");
		System.out.println("consumerKey "+consumerKey+" consumerSecret "+consumerSecret+" accessToken "+accessToken+" accessTokenSecret "+accessTokenSecret);		
		ConfigurationBuilder cb = new ConfigurationBuilder();
		cb.setDebugEnabled(true)
		        .setOAuthConsumerKey(consumerKey)
				.setOAuthConsumerSecret(consumerSecret)
				.setOAuthAccessToken(accessToken)
				.setOAuthAccessTokenSecret(accessTokenSecret);
		TwitterFactory tf = new TwitterFactory(cb.build());
		System.out.println("Twitter Factory "+tf);
		System.out.println("Code testing purpose ");
		Twitter twitter = tf.getInstance();
		System.out.println("Twitter object "+twitter);
		Map<String, String> trendDetails = new HashMap<String, String>();
		try {
			Trends trends = twitter.getPlaceTrends(Integer.parseInt(trendPlace));
			System.out.println("After API call");
			int count = 0;
			for (Trend trend : trends.getTrends()) {
				if (count < Integer.parseInt(trendCount)) {
					trendDetails.put(trend.getName(), trend.getURL());
					count++;
				}
			}
		} catch (TwitterException e) {
			trendDetails.put("test", "MyTweet");
            //trendDetails.put("Twitter Exception", e.getMessage());
			System.out.println("Twitter exception "+e.getMessage());

		}catch (Exception e) {
			trendDetails.put("test", "MyTweet");
            System.out.println("Exception "+e.getMessage());
		}
		return trendDetails;
	}

}
